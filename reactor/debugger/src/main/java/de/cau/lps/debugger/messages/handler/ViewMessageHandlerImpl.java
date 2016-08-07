package de.cau.lps.debugger.messages.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.exception.UnknownMessageTypeException;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.view.AddBreakpointMessage;
import de.cau.lps.debugger.messages.incoming.view.DisableAllBreakpointsMessage;
import de.cau.lps.debugger.messages.incoming.view.EnableAllBreakpointsMessage;
import de.cau.lps.debugger.messages.incoming.view.RemoveAllBreakpointsMessage;
import de.cau.lps.debugger.messages.incoming.view.RemoveBreakpointMessage;
import de.cau.lps.debugger.messages.incoming.view.RunToEndOfMethodMessage;
import de.cau.lps.debugger.messages.incoming.view.RunToNextBreakpointMessage;
import de.cau.lps.debugger.messages.incoming.view.StartReplayMessage;
import de.cau.lps.debugger.messages.incoming.view.StepMessage;
import de.cau.lps.debugger.messages.incoming.view.StepOverMessage;
import de.cau.lps.debugger.messages.incoming.view.StopReplayMessage;
import de.cau.lps.debugger.program.Debugger;
import de.cau.lps.debugger.program.DebuggerImpl;

/**
 * Handles incoming view messages and calls required methods on the {@link DebuggerImpl}.
 * 
 * @author Thomas Ulrich
 */
public class ViewMessageHandlerImpl implements ViewMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(ViewMessageHandlerImpl.class);

    private Debugger debugger;

    /**
     * Initializes a new instance of the {@link ViewMessageHandler} class.
     * 
     * @param debugger
     *            A {@link DebuggerImpl} used to handle state changes.
     */
    public ViewMessageHandlerImpl(Debugger debugger) {
        this.debugger = debugger;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.cau.lps.debugger.messages.handler.ViewMessageHandler#handleIncomingViewMessage(de.cau.lps.debugger.messages.
     * AbstractMessage)
     */
    @Override
    public void handleIncomingViewMessage(AbstractMessage message) throws UnknownMessageTypeException {
        if (message instanceof StepMessage) {
            this.handleStepMessage((StepMessage) message);
        } else if (message instanceof StepOverMessage) {
            this.handleStepOverMessage((StepOverMessage) message);
        } else if (message instanceof AddBreakpointMessage) {
            this.handleAddBreakpointMessage((AddBreakpointMessage) message);
        } else if (message instanceof RemoveBreakpointMessage) {
            this.handleRemoveBreakpointMessage((RemoveBreakpointMessage) message);
        } else if (message instanceof RunToNextBreakpointMessage) {
            this.handleRunToNextBreakpointMessage((RunToNextBreakpointMessage) message);
        } else if (message instanceof StartReplayMessage) {
            this.handleStartReplayMessage((StartReplayMessage) message);
        } else if (message instanceof StopReplayMessage) {
            this.handleStopReplayMessage((StopReplayMessage) message);
        } else if (message instanceof RemoveAllBreakpointsMessage) {
            this.handleRemoveAllBreakpointsMessage((RemoveAllBreakpointsMessage) message);
        } else if (message instanceof EnableAllBreakpointsMessage) {
            this.handleEnableAllBreakpointsMessage((EnableAllBreakpointsMessage) message);
        } else if (message instanceof DisableAllBreakpointsMessage) {
            this.handleDisableAllBreakpointsMessage((DisableAllBreakpointsMessage) message);
        } else if (message instanceof RunToEndOfMethodMessage) {
            this.handleRunToEndOfMethodMessage((RunToEndOfMethodMessage) message);
        } else {
            throw new UnknownMessageTypeException("Cannot handle of type '" + message.getClass().toString() + "'.");
        }
    }

    /**
     * Handles a {@link StepMessage}.
     * 
     * @param received
     *            The {@link StepMessage}.
     */
    private void handleStepMessage(StepMessage received) {
        logger.debug("Received StepMessage from view.");
        this.debugger.sendStepSignalToRuntime();
        /*
         * Sending 'STEP' cancels a potential step-over the idea is that the user may use "stepover" to step over a
         * built-in function since the stepovernextcall flag only gets disabled when a call is pushed onto the call
         * stack, the flag will remain true even after the built-in function has exited. If the user presses 'STEP'
         * afterwards again, then we'll cancel the flag altogether, ensuring that the debugger doesn't step over the
         * next user defined function.
         *
         */
        this.debugger.getState().setStepOverNextCall(false);
    }

    /**
     * Handles a {@link StepOverMessage}.
     * 
     * @param received
     *            The {@link StepOverMessage}.
     */
    private void handleStepOverMessage(StepOverMessage received) {
        logger.debug("Received StepOver message from view.");
        logger.debug("Next method call will be marked for automatic stepping purposes.");
        this.debugger.getState().setStepOverNextCall(true);
        this.debugger.sendStepSignalToRuntime();
    }

    /**
     * Handles a {@link AddBreakpointMessage}.
     * 
     * @param received
     *            The {@link AddBreakpointMessage}.
     */
    private void handleAddBreakpointMessage(AddBreakpointMessage received) {
        logger.debug("Adding breakpoint @" + received.getPosition() + ".");
        this.debugger.getBreakPoints().add(received.getPosition());
    }

    /**
     * Handles a {@link RemoveBreakpointMessage}.
     * 
     * @param received
     *            The {@link RemoveBreakpointMessage}.
     */
    private void handleRemoveBreakpointMessage(RemoveBreakpointMessage received) {
        logger.debug("Removing breakpoint @" + received.getPosition() + ".");
        this.debugger.getBreakPoints().remove(received.getPosition());
    }

    /**
     * Handles a {@link RunToNextBreakpointMessage}.
     * 
     * @param received
     *            The {@link RunToNextBreakpointMessage}.
     */
    private void handleRunToNextBreakpointMessage(RunToNextBreakpointMessage received) {
        logger.debug("Received RunToNextBreakpoint message, enabling automatic stepping.");
        this.debugger.enableAutoStepping();
        this.debugger.sendStepSignalToRuntime();
    }

    /**
     * Handles a {@link StartReplayMessage}.
     * 
     * @param message
     *            The {@link StartReplayMessage} to handle.
     */
    private void handleStartReplayMessage(StartReplayMessage message) {
        logger.debug("Received StartReplayMessage, enabling replay.");
        try {
            this.debugger.startReplay(message.getReplayFromCallId());
            logger.debug("Replay enabled.");
        } catch (Exception e) {
            this.debugger.stopReplay();
            logger.error("An error occurred while starting replay, exception was " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * Handles a {@link StopReplayMessage}.
     * 
     * @param message
     *            The {@link StopReplayMessage} to handle.
     */
    private void handleStopReplayMessage(StopReplayMessage message) {
        logger.debug("Received StopReplayMessage, disabling replay.");
        this.debugger.stopReplay();
        logger.debug("Replay disabled.");
    }

    /**
     * Handles a {@link RemoveAllBreakpointsMessage}. It clears the list of breakpoints.
     * 
     * @param message
     *            The message to handle.
     */
    private void handleRemoveAllBreakpointsMessage(RemoveAllBreakpointsMessage message) {
        logger.debug("Received RemoveAllBreakpointsMessage, clearing breakpoints.");
        this.debugger.getBreakPoints().clear();
        logger.debug("Breakpoints cleared.");
    }

    /**
     * Handles a {@link DisableAllBreakpointsMessage}.
     * 
     * @param message
     *            The {@link DisableAllBreakpointsMessage}.
     */
    private void handleDisableAllBreakpointsMessage(DisableAllBreakpointsMessage message) {
        logger.debug("Disabling all breakpoints.");
        this.debugger.setBreakpointsEnabled(false);
        logger.debug("All breakpoints disabled.");
    }

    /**
     * Handles a {@link EnableAllBreakpointsMessage}.
     * 
     * @param message
     *            The {@link EnableAllBreakpointsMessage}.
     */
    private void handleEnableAllBreakpointsMessage(EnableAllBreakpointsMessage message) {
        logger.debug("Enabling all breakpoints.");
        this.debugger.setBreakpointsEnabled(true);
        logger.debug("All breakpoints enabled.");
    }

    /**
     * Handles a {@link RunToEndOfMethodMessage}.
     * 
     * @param message
     *            The {@link RunToEndOfMethodMessage}.
     */
    private void handleRunToEndOfMethodMessage(RunToEndOfMethodMessage message) {
        logger.debug("Running to end of current method.");
        if (this.debugger.getState().getCallStack().size() > 0) {
            this.debugger.getState().getCallStack().peek().setMarked(true);
        }
        this.debugger.enableAutoStepping();
        this.debugger.sendStepSignalToRuntime();
        logger.debug("Now running to the end of current method.");
    }
}
