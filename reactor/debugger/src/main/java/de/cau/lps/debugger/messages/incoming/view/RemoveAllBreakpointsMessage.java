package de.cau.lps.debugger.messages.incoming.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal the removal of all breakpoints.
 * 
 * @author Thomas Ulrich
 *
 */
public class RemoveAllBreakpointsMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link RemoveAllBreakpointsMessage} class.
     * 
     * @param message
     *            A String encapsulating the 'remove all breakpoints' message.
     */
    public RemoveAllBreakpointsMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return IncomingMessageType.REMOVEALLBREAKPOINTS.toString();
    }
}
