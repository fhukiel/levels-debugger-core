package de.cau.lps.debugger.messages.incoming.runtime;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal that the end of the replay tape has been reached.
 * 
 * @author Thomas Ulrich
 */
public class EndOfTapeMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link EndOfTapeMessage} class.
     * 
     * @param message
     *            The 'no more messages to play' message.
     */
    public EndOfTapeMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return IncomingMessageType.ENDOFTAPE.toString();
    }
}
