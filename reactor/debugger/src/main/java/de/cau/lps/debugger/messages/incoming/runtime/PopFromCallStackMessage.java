package de.cau.lps.debugger.messages.incoming.runtime;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal the removal of a call from the call stack.
 * 
 * @author Thomas Ulrich
 *
 */
public class PopFromCallStackMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link PopFromCallStackMessage} class.
     * 
     * @param message
     *            A message String containing the 'Pop a call from the call stack' message.
     */
    public PopFromCallStackMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return IncomingMessageType.POPFROMCALLSTACK.toString();
    }
}
