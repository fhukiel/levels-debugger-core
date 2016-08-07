package de.cau.lps.debugger.messages.outgoing.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;

/**
 * This message is sent to the view once auto stepping is disabled.
 * 
 * @author Thomas Ulrich
 */
public class AutoSteppingDisabledMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link AutoSteppingDisabledMessage} class.
     * 
     * @param message
     *            The 'auto stepping disabled' message.
     */
    public AutoSteppingDisabledMessage(String message) {
    }

    @Override
    protected String getStringRepresentation() {
        return OutgoingMessageType.AUTOSTEPPINGDISABLED.toString();
    }
}
