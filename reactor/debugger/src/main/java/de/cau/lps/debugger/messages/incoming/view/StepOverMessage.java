package de.cau.lps.debugger.messages.incoming.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal the stepping over a instruction without halting execution.
 * 
 * @author Thomas Ulrich
 *
 */
public class StepOverMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link StepOverMessage} class.
     * 
     * @param message
     *            A String representing the 'step over this instruction without halting' message.
     */
    public StepOverMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return IncomingMessageType.STEPOVER.toString();
    }
}
