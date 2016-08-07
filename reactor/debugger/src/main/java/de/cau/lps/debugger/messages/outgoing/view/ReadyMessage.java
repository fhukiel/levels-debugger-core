package de.cau.lps.debugger.messages.outgoing.view;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.program.DebuggerImpl;

/**
 * Message used to signal to the view that both parties are now ready. {@link DebuggerImpl} and is sent to the runtime.
 * 
 * @author Thomas Ulrich
 *
 */
public class ReadyMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link ReadyMessage}.
     * 
     * @param message
     *            String representing the 'both parties are ready' message.
     */
    public ReadyMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return OutgoingMessageType.READY.toString();
    }
}
