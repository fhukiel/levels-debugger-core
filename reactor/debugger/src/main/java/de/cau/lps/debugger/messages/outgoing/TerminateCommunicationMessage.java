package de.cau.lps.debugger.messages.outgoing;

import de.cau.lps.debugger.messages.AbstractMessage;

/**
 * Message used to signal that the receiver should terminate communications with the sender.
 * 
 * @author Thomas Ulrich
 */
public class TerminateCommunicationMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link TerminateCommunicationMessage} class.
     * 
     * @param message
     *            A String representing the 'terminate communication with peer' message.
     */
    public TerminateCommunicationMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return OutgoingMessageType.TERMINATECOMMUNICATION.toString();
    }
}
