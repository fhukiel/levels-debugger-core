package de.cau.lps.debugger.messages.incoming.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal the running of the source code to the next breakpoint.
 * 
 * @author Thomas Ulrich
 *
 */
public class RunToNextBreakpointMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link RunToNextBreakpointMessage} class.
     * 
     * @param message
     *            A String encapsulating the 'run code to next break point' message.
     */
    public RunToNextBreakpointMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return IncomingMessageType.RUNTONEXTBREAKPOINT.toString();
    }
}
