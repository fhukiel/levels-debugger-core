package de.cau.lps.debugger.messages.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.exception.UnknownMessageTypeException;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.runtime.EndOfTapeMessage;
import de.cau.lps.debugger.messages.incoming.runtime.PopFromCallStackMessage;
import de.cau.lps.debugger.messages.incoming.runtime.PushOntoCallStackMessage;
import de.cau.lps.debugger.messages.incoming.runtime.UpdatePositionMessage;
import de.cau.lps.debugger.messages.incoming.runtime.UpdateTableMessage;
import de.cau.lps.debugger.program.Debugger;

/**
 * Handles incoming runtime messages.
 * 
 * @author Thomas Ulrich
 */
public class RuntimeMessageHandlerImpl implements RuntimeMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(RuntimeMessageHandlerImpl.class);

    private Debugger debugger;

    /**
     * Initializes a new instance of the {@link RuntimeMessageHandlerImpl} class.
     * 
     * @param debugger
     *            A debugger used to store state.
     */
    public RuntimeMessageHandlerImpl(Debugger debugger) {
        this.debugger = debugger;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.handler.RuntimeMessageHandler#handleIncomingRuntimeMessage(de.cau.lps.debugger.
     * messages.AbstractMessage)
     */
    @Override
    public void handleIncomingRuntimeMessage(AbstractMessage message) throws UnknownMessageTypeException {
        this.debugger.recordMessage(message);
        if (message instanceof PushOntoCallStackMessage) {
            this.handlePushOntoCallStackMessage((PushOntoCallStackMessage) message);
        } else if (message instanceof PopFromCallStackMessage) {
            this.handlePopFromCallStackMessage((PopFromCallStackMessage) message);
        } else if (message instanceof UpdateTableMessage) {
            this.handleUpdateTableMessage((UpdateTableMessage) message);
        } else if (message instanceof UpdatePositionMessage) {
            this.handleUpdatePositionMessage((UpdatePositionMessage) message);
        } else if (message instanceof EndOfTapeMessage) {
            this.handleEndOfTapeMessage((EndOfTapeMessage) message);
        } else {
            throw new UnknownMessageTypeException("Cannot handle of type '" + message.getClass().toString() + "'.");
        }
    }

    /**
     * Handles a {@link PushOntoCallStackMessage}.
     * 
     * @param received
     *            The {@link PushOntoCallStackMessage}
     */
    private void handlePushOntoCallStackMessage(PushOntoCallStackMessage received) {
        logger.debug("Pushing method call '" + received.getMethodName());
        this.debugger.pushOntoCallStack(
            new Call(received.getMethodName(), received.getArguments(), received.getId(), received.getOptions()));
    }

    /**
     * Handles a {@link PopFromCallStackMessage}.
     * 
     * @param received
     *            The {@link PopFromCallStackMessage}.
     */
    private void handlePopFromCallStackMessage(PopFromCallStackMessage received) {
        logger.debug("Popping from CallStack.");
        Call poppedCall = this.debugger.popFromCallStack();
        logger.debug("Successfully popped method '" + poppedCall.getMethodName() + "' from stack.");
    }

    /**
     * Handles a {@link UpdateTableMessage}.
     * 
     * @param received
     *            The {@link UpdateTableMessage}.
     */
    private void handleUpdateTableMessage(UpdateTableMessage received) {
        logger.debug("Updating Table, putting Variable '" + received.getVariableName() + "' with value '"
            + received.getVariableValue() + "' at address '" + received.getVariableAddress());

        Variable var = new Variable(received.getVariableName(), received.getVariableValue(),
            received.getVariableAddress(), received.getOptions());
        this.debugger.updateTable(var);
    }

    /**
     * Handles a {@link UpdatePositionMessage}.
     * 
     * @param received
     *            The {@link UpdatePositionMessage}.
     */
    private void handleUpdatePositionMessage(UpdatePositionMessage received) {
        logger.debug("Updating position to " + received.getPosition() + ".");
        this.debugger.setPosition(received.getPosition());
    }

    private void handleEndOfTapeMessage(EndOfTapeMessage message) {
        logger.debug("End of replay tape: " + message.toString());
        this.debugger.signalEndOfReplayTape();
    }
}
