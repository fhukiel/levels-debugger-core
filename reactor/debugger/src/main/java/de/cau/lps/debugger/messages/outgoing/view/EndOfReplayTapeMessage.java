package de.cau.lps.debugger.messages.outgoing.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;

/**
 * This message is sent to the view once runtime replay has finished.
 * 
 * @author Thomas Ulrich
 */
public class EndOfReplayTapeMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link EndOfReplayTapeMessage} class.
     * 
     * @param message
     *            The 'runtime replay finished' message.
     */
    public EndOfReplayTapeMessage(String message) {
    }

    @Override
    protected String getStringRepresentation() {
        return OutgoingMessageType.ENDOFREPLAYTAPE.toString();
    }
}
