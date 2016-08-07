package de.cau.lps.debugger.messages.incoming.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal the stop of a replay.
 * 
 * @author Thomas Ulrich
 */
public class StopReplayMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link StopReplayMessage} class.
     * 
     * @param message
     *            The String representing the 'stop replay' message.
     */
    public StopReplayMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return IncomingMessageType.STOPREPLAY.toString();
    }
}
