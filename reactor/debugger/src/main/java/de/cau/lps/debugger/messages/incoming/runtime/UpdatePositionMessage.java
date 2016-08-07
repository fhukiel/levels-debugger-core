package de.cau.lps.debugger.messages.incoming.runtime;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.PositionalMessage;

/**
 * A message signaling a update to the current position within the source code. Message originates from the runtime.
 * 
 * @author Thomas Ulrich
 *
 */
public class UpdatePositionMessage extends PositionalMessage {

    /**
     * Initializes a new instance of the {@link UpdatePositionMessage} class.
     * 
     * @param message
     *            The String containing the 'Update current position to XYZ' message.
     * @throws MessageWronglyFormattedException
     *             Thrown if message is wrongly formatted.
     */
    public UpdatePositionMessage(String message) throws MessageWronglyFormattedException {
        super(message, IncomingMessageType.UPDATEPOSITION);
        this.requiresAcknowledge = true;
    }
}
