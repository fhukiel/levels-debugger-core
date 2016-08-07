package de.cau.lps.debugger.messages.handler;

import de.cau.lps.debugger.exception.UnknownMessageTypeException;
import de.cau.lps.debugger.messages.AbstractMessage;

/**
 * Handles incoming runtime messages.
 * 
 * @author Thomas Ulrich
 *
 */
public interface RuntimeMessageHandler {

    /**
     * Handles incoming runtime messages.
     * 
     * @param message
     *            The received runtime message.
     * @throws UnknownMessageTypeException
     *             Thrown if the received message is unknown and cannot be handled.
     */
    public void handleIncomingRuntimeMessage(AbstractMessage message) throws UnknownMessageTypeException;
}
