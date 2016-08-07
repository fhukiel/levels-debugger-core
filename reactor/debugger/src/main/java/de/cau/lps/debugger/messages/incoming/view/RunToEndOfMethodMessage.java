package de.cau.lps.debugger.messages.incoming.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal running to the end of the current method.
 * 
 * @author Thomas Ulrich
 *
 */
public class RunToEndOfMethodMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link RunToEndOfMethodMessage} class.
     * 
     * @param message
     *            A String encapsulating the 'run to end of current method' message.
     */
    public RunToEndOfMethodMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return IncomingMessageType.RUNTOENDOFMETHOD.toString();
    }
}
