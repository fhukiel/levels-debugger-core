package de.cau.lps.debugger.messages.incoming.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal the stepping to the next instruction.
 * 
 * @author Thomas Ulrich
 *
 */
public class StepMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link StepMessage} class.
     * 
     * @param message
     *            The String representing the 'step to next instruction' message.
     */
    public StepMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return IncomingMessageType.STEP.toString();
    }
}
