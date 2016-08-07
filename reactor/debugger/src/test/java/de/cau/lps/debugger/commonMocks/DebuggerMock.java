package de.cau.lps.debugger.commonMocks;

import java.util.HashSet;
import java.util.Set;

import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.exception.MissingReplayInformationException;
import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.program.Debugger;
import de.cau.lps.debugger.program.DebuggerState;
import de.cau.lps.debugger.replay.MessageRecorder;

/**
 * A mock object implementing the {@link Debugger} interface.
 * 
 * @author Thomas Ulrich
 */
public class DebuggerMock implements Debugger {

    private DebuggerState state;
    private MessageRecorder messageRecorder;
    private boolean replay;
    private boolean stepMessageSent;
    private boolean breakpointsEnabled;
    private String replayFrom;
    private Set<Position> breakpoints;

    /**
     * Initializes a new instance of the {@link DebuggerMock} class.
     */
    public DebuggerMock() {
        this.state = new DebuggerState();
        this.state.setVariableTable(new VariableTableMock());
        this.messageRecorder = new MessageRecorder();
        this.replay = false;
        this.stepMessageSent = false;
        this.breakpointsEnabled = true;
        this.breakpoints = new HashSet<Position>();
    }

    @Override
    public void recordMessage(AbstractMessage message) {
        this.messageRecorder.record(message);
    }

    @Override
    public void pushOntoCallStack(Call call) {
        this.state.getCallStack().push(call);
    }

    @Override
    public Call popFromCallStack() {
        return this.state.getCallStack().pop();
    }

    @Override
    public void updateTable(Variable var) {
        this.state.getVariableTable().update(new ScopeIdentifier(this.state.getLocalScopeIdentifier()), var);
    }

    @Override
    public void startReplay(String replayFromCallId) throws MissingReplayInformationException {
        this.replay = true;
        this.replayFrom = replayFromCallId;
    }

    @Override
    public void stopReplay() {
        this.replay = false;
    }

    @Override
    public void setPosition(Position newPosition) {
        this.state.setPosition(newPosition);
    }

    @Override
    public void sendStepSignalToRuntime() {
        this.stepMessageSent = true;
    }

    @Override
    public DebuggerState getState() {
        return state;
    }

    @Override
    public void signalEndOfReplayTape() {
    }

    /**
     * Gets the value of the replay flag.
     * 
     * @return True if replay is enabled, otherwise false.
     */
    public boolean isReplay() {
        return replay;
    }

    /**
     * Gets the value of the stepMessageSent flag.
     * 
     * @return True if step message was sent, otherwise false.
     */
    public boolean isStepMessageSent() {
        return stepMessageSent;
    }

    /**
     * Gets the Id of the call from which to replay from.
     * 
     * @return The Id.
     */
    public String getReplayFrom() {
        return replayFrom;
    }

    /**
     * Gets the current time machine.
     * 
     * @return The current {@link MessageRecorder}.
     */
    public MessageRecorder getMessageRecorder() {
        return messageRecorder;
    }

    @Override
    public void enableAutoStepping() {
        this.state.setAutoStep(true);
        this.sendStepSignalToRuntime();
    }

    @Override
    public void disableAutoStepping() {
        this.state.setAutoStep(false);
    }

    @Override
    public Set<Position> getBreakPoints() {
        return this.breakpoints;
    }

    @Override
    public boolean isBreakpoint(Position position) {
        return this.breakpoints.contains(position);
    }

    @Override
    public void setBreakpointsEnabled(boolean breakpointsEnabled) {
        this.breakpointsEnabled = breakpointsEnabled;
    }

    @Override
    public boolean isBreakpointsEnabled() {
        return this.breakpointsEnabled;
    }
}
