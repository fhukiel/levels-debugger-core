package de.cau.lps.debugger.messages.incoming.view;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.PositionalMessage;

/**
 * Message used to signal the adding of a Breakpoint.
 * 
 * @author Thomas Ulrich
 *
 */
public class AddBreakpointMessage extends PositionalMessage {

    /**
     * Initializes a new instance of the {@link AddBreakpointMessage} class.
     * 
     * @param message
     *            A message String containing the 'Add breakpoint at position XYZ' message.
     * @throws MessageWronglyFormattedException
     *             Thrown if passed String is wrongly formatted.
     */
    public AddBreakpointMessage(String message) throws MessageWronglyFormattedException {
        super(message, IncomingMessageType.ADDBREAKPOINT);
    }
}
