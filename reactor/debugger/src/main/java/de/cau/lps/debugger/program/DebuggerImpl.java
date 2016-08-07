package de.cau.lps.debugger.program;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.channel.ChannelInitializer;
import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.channel.RuntimeReplayChannel;
import de.cau.lps.debugger.channel.listener.RuntimeChannelListener;
import de.cau.lps.debugger.channel.listener.ViewChannelListener;
import de.cau.lps.debugger.channel.talker.RuntimeChannelTalker;
import de.cau.lps.debugger.channel.talker.ViewChannelTalker;
import de.cau.lps.debugger.common.BreakpointManager;
import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.exception.MissingReplayInformationException;
import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.handler.RuntimeMessageHandlerImpl;
import de.cau.lps.debugger.messages.handler.ViewMessageHandlerImpl;
import de.cau.lps.debugger.replay.MessageRecorder;
import de.cau.lps.debugger.replay.RuntimeReplayListener;

/**
 * Wraps the actual debugging logic and handles communication between the runtime and view.
 * 
 * @author Thomas Ulrich
 *
 */
public class DebuggerImpl implements Runnable, Debugger {

    private static Logger logger = LoggerFactory.getLogger(DebuggerImpl.class);

    private CommunicationChannel viewChannel;
    private CommunicationChannel runtimeChannel;
    private Stack<CommunicationChannel> shadowRuntimeChannels;
    private Thread runtimeReplayListenerThread;

    private ViewChannelTalker viewTalker;
    private RuntimeChannelTalker runtimeTalker;

    private DebuggerState state;
    private Stack<DebuggerState> shadowStates;
    private BreakpointManager breakpointManager;

    private CountDownLatch latch;
    private Map<String, DebuggerState> entryPoints;
    private MessageRecorder messageRecorder;

    /**
     * Initializes a new instance of the {@link DebuggerImpl} class.
     * 
     * @param viewChannel
     *            A {@link CommunicationChannel} object used to communicate with the view.
     * @param runtimeChannel
     *            A {@link CommunicationChannel} object used to communicate with the runtime.
     */
    public DebuggerImpl(CommunicationChannel viewChannel, CommunicationChannel runtimeChannel) {
        this.viewChannel = viewChannel;
        this.viewTalker = new ViewChannelTalker(viewChannel);
        this.runtimeChannel = runtimeChannel;
        this.runtimeTalker = new RuntimeChannelTalker(runtimeChannel);
        this.state = new DebuggerState();
        this.shadowStates = new Stack<DebuggerState>();
        this.shadowRuntimeChannels = new Stack<CommunicationChannel>();
        this.latch = new CountDownLatch(1);
        this.breakpointManager = new BreakpointManager();
        this.messageRecorder = new MessageRecorder();
        this.entryPoints = new HashMap<String, DebuggerState>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // Open up channels
        this.openChannels();

        // Attach listeners
        ViewChannelListener viewListener = new ViewChannelListener(viewChannel, new ViewMessageHandlerImpl(this),
            this.latch);
        RuntimeChannelListener runtimeListener = new RuntimeChannelListener(runtimeChannel,
            new RuntimeMessageHandlerImpl(this), this.latch);
        new Thread(viewListener).start();
        new Thread(runtimeListener).start();

        // Send ready signal to view and wait for the first channel to close
        try {
            this.viewTalker.sendReady();
            this.latch.await();
        } catch (InterruptedException | IOException e) {
            logger.error("An exception occurred during execution, exception was " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#recordMessage(de.cau.lps.debugger.messages.AbstractMessage)
     */
    @Override
    public void recordMessage(AbstractMessage message) {
        this.messageRecorder.record(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#pushOntoCallStack(de.cau.lps.debugger.common.Call)
     */
    @Override
    public void pushOntoCallStack(Call call) {
        call.setMarked(this.state.isStepOverNextCall());
        this.state.setStepOverNextCall(false);
        this.state.getCallStack().push(call);
        this.entryPoints.put(call.getAssociatedPushMessageID(), new DebuggerState(this.state));

        if (call.isMarked()) {
            logger.debug("This call has been marked as 'Step over'. Automatic stepping has been enabled.");
            this.enableAutoStepping();
        }
        this.sendCallStackToView();
        this.sendVariableTableToView();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#popFromCallStack()
     */
    @Override
    public Call popFromCallStack() {
        String poppedScope = this.state.getLocalScopeIdentifier();
        Call poppedCall = this.state.getCallStack().pop();
        if (poppedCall.isMarked()) {
            logger.debug("Marked call has been popped from CallStack, automatic stepping has been disabled.");
            this.disableAutoStepping();
            this.viewTalker.setDiscardAll(false);
        }
        this.state.getVariableTable().dropScope(new ScopeIdentifier(poppedScope));
        this.sendCallStackToView();
        this.sendVariableTableToView();
        return poppedCall;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#updateTable(java.lang.String, java.lang.String)
     */
    @Override
    public void updateTable(Variable variable) {
        this.state.getVariableTable().update(new ScopeIdentifier(this.state.getLocalScopeIdentifier()), variable);
        this.sendVariableTableToView();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#startReplay(java.lang.String)
     */
    @Override
    public void startReplay(String replayFromCallId) throws MissingReplayInformationException {
        DebuggerState entryPoint = new DebuggerState(this.entryPoints.get(replayFromCallId));
        this.messageRecorder.setRecordingEnabled(false);
        this.shadowStates.push(new DebuggerState(this.state));
        this.revertToState(entryPoint);
        // Disable auto-stepping and suppression, otherwise a replay from a "run to next breakpoint" will immediately
        // run to the end of the tape and/or no updates are visible to the view
        this.state.setAutoStep(false);
        this.viewTalker.setDiscardAll(false);
        this.switchToRuntimeReplay(replayFromCallId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#stopReplay()
     */
    @Override
    public void stopReplay() {
        this.runtimeReplayListenerThread.interrupt();

        /*
         * Stop replay stops replay altogether, thus reverting to the state before the first "startReplay" message.
         * Channel is also reset to the "real" runtime channel
         */
        this.runtimeChannel = this.shadowRuntimeChannels.firstElement();
        this.shadowRuntimeChannels.clear();
        this.runtimeTalker = new RuntimeChannelTalker(this.runtimeChannel);
        DebuggerState shadowState = this.shadowStates.firstElement();
        this.shadowStates.clear();
        this.revertToState(shadowState);
        this.messageRecorder.setRecordingEnabled(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#setPosition(de.cau.lps.debugger.common.Position)
     */
    @Override
    public void setPosition(Position newPosition) {
        this.state.setPosition(newPosition);
        this.sendPositionToView();
        this.handleAutoStepping();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#getState()
     */
    @Override
    public DebuggerState getState() {
        return this.state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#sendStepSignalToRuntime()
     */
    @Override
    public void sendStepSignalToRuntime() {
        logger.debug("Sending step signal to runtime.");
        try {
            this.runtimeTalker.sendStepSignal();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("An error occurred while sending step signal to runtime, exception was " + e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#signalEndOfReplayTape()
     */
    @Override
    public void signalEndOfReplayTape() {
        logger.debug("Signalling end of replay tape to view.");
        try {
            this.disableAutoStepping();
            this.viewTalker.sendEndOfReplayTape();
        } catch (IOException e) {
            logger.error(
                "An error occurred while sending end of replay message to view, exception was " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#enableAutoStepping()
     */
    @Override
    public void enableAutoStepping() {
        logger.debug("Enabling auto-stepping.");
        this.state.setAutoStep(true);
        this.viewTalker.setDiscardAll(true);
        try {
            this.viewTalker.sendAutoSteppingEnabled();
            logger.debug("Auto-stepping successfully enabled.");
        } catch (IOException e) {
            logger.error("An error occurred while enabling auto-stepping, exception was " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#disableAutoStepping()
     */
    @Override
    public void disableAutoStepping() {
        logger.debug("Disabling auto-stepping.");
        this.state.setAutoStep(false);
        this.viewTalker.setDiscardAll(false);
        this.sendCallStackToView();
        this.sendPositionToView();
        this.sendVariableTableToView();
        try {
            this.viewTalker.sendAutoSteppingDisabled();
            logger.debug("Auto-stepping successfully disabled.");
        } catch (IOException e) {
            logger.error("An error occurred while disabling auto-stepping, exception was " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#isBreakpoint(de.cau.lps.debugger.common.Position)
     */
    @Override
    public boolean isBreakpoint(Position position) {
        return this.breakpointManager.isBreakpoint(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#getBreakPoints()
     */
    @Override
    public Set<Position> getBreakPoints() {
        return this.breakpointManager.getAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#isBreakpointsEnabled()
     */
    @Override
    public boolean isBreakpointsEnabled() {
        return breakpointManager.isBreakpointsEnabled();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.program.Debugger#setBreakpointsEnabled(boolean)
     */
    @Override
    public void setBreakpointsEnabled(boolean breakpointsEnabled) {
        this.breakpointManager.setBreakpointsEnabled(breakpointsEnabled);
    }

    private void handleAutoStepping() {
        if (this.isBreakpoint(this.state.getPosition()) && this.state.isAutoStep()) {
            logger.debug("Breakpoint hit, automatic stepping disabled.");
            this.disableAutoStepping();
        } else if (this.state.isAutoStep()) {
            logger.debug("Automatic stepping is enabled.");
            this.sendStepSignalToRuntime();
        }
    }

    private void revertToState(DebuggerState shadow) {
        this.state = shadow;
        this.sendPositionToView();
        this.sendCallStackToView();
        this.sendVariableTableToView();
    }

    private boolean openChannels() {
        boolean channelsReady = false;
        try {
            // Start threads waiting for the Channels to get ready
            Thread viewChannelInitializer = new Thread(new ChannelInitializer(viewChannel));
            Thread runtimeChannelInitializer = new Thread(new ChannelInitializer(runtimeChannel));

            viewChannelInitializer.start();
            runtimeChannelInitializer.start();
            viewChannelInitializer.join();
            runtimeChannelInitializer.join();

            channelsReady = viewChannel.isAvailable() && runtimeChannel.isAvailable();

        } catch (Exception e) {
            logger.error("Error while opening up communication channels, exception was " + e.getMessage(), e);
        }

        return channelsReady;
    }

    private void switchToRuntimeReplay(String callId) {
        logger.debug("Switching to runtime replay.");
        Object synchronizer = new Object();
        this.shadowRuntimeChannels.push(this.runtimeChannel);
        this.runtimeChannel = new RuntimeReplayChannel(synchronizer);
        this.runtimeTalker = new RuntimeChannelTalker(this.runtimeChannel);
        MessageRecorder messageRecorderClone = new MessageRecorder(this.messageRecorder);
        messageRecorderClone.forwardToCall(callId);

        // Start replaying messages
        RuntimeReplayListener listener = new RuntimeReplayListener(messageRecorderClone,
            new RuntimeMessageHandlerImpl(this), synchronizer);
        this.runtimeReplayListenerThread = new Thread(listener);
        this.runtimeReplayListenerThread.start();
        logger.debug("Switch to replay complete.");
    }

    private void sendCallStackToView() {
        try {
            this.viewTalker.sendCallStack(this.state.getCallStack());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while sending call stack to view, exception was " + e.getMessage(), e);
        }
    }

    private void sendVariableTableToView() {
        try {
            ScopeIdentifier scopeId = new ScopeIdentifier(this.state.getLocalScopeIdentifier(),
                this.state.getTopMostCallOptions());
            List<Variable> visibles = this.state.getVariableTable().getVisibleInScope(scopeId);
            logger.debug("Visibles size: " + visibles.size());
            this.viewTalker.sendVariableTable(visibles);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while sending variable table to view, exception was " + e.getMessage(), e);
        }
    }

    private void sendPositionToView() {
        try {
            this.viewTalker.sendPosition(this.state.getPosition());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while sending position to view, exception was " + e.getMessage(), e);
        }
    }
}