package de.cau.lps.debugger.messages.outgoing.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;

/**
 * This message is sent to the view once auto stepping is enabled.
 * 
 * @author Thomas Ulrich
 */
public class AutoSteppingEnabledMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link AutoSteppingEnabledMessage} class.
     * 
     * @param message
     *            The 'auto stepping enabled' message.
     */
    public AutoSteppingEnabledMessage(String message) {
    }

    @Override
    protected String getStringRepresentation() {
        return OutgoingMessageType.AUTOSTEPPINGENABLED.toString();
    }
}
