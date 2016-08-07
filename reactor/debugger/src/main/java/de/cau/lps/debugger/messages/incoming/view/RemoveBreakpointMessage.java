package de.cau.lps.debugger.messages.incoming.view;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.PositionalMessage;

/**
 * Message used to signal the removal of a breakpoint.
 * 
 * @author Thomas Ulrich
 *
 */
public class RemoveBreakpointMessage extends PositionalMessage {

    /**
     * Initializes a new instance of the {@link RemoveBreakpointMessage} class.
     * 
     * @param message
     *            The String containing the 'Remove breakpoint at position XYZ' message.
     * @throws MessageWronglyFormattedException
     *             Thrown if message is wrongly formatted.
     */
    public RemoveBreakpointMessage(String message) throws MessageWronglyFormattedException {
        super(message, IncomingMessageType.REMOVEBREAKPOINT);
    }
}
