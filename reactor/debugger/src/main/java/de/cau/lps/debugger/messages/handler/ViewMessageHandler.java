package de.cau.lps.debugger.messages.handler;

import de.cau.lps.debugger.exception.UnknownMessageTypeException;
import de.cau.lps.debugger.messages.AbstractMessage;

/**
 * Handles incoming view messages.
 * 
 * @author Thomas Ulrich
 *
 */
public interface ViewMessageHandler {

    /**
     * Handles incoming view messages.
     * 
     * @param message
     *            The received view message.
     * @throws UnknownMessageTypeException
     *             Thrown if the received message is unknown and cannot be handled.
     */
    public void handleIncomingViewMessage(AbstractMessage message) throws UnknownMessageTypeException;
}
