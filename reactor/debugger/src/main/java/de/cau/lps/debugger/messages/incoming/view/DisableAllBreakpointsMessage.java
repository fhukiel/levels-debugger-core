package de.cau.lps.debugger.messages.incoming.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal the disabling of all breakpoints.
 * 
 * @author Thomas Ulrich
 *
 */
public class DisableAllBreakpointsMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link DisableAllBreakpointsMessage} class.
     * 
     * @param message
     *            A String encapsulating the 'disable all breakpoints' message.
     */
    public DisableAllBreakpointsMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return IncomingMessageType.DISABLEALLBREAKPOINTS.toString();
    }
}
