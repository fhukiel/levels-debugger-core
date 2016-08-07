package de.cau.lps.debugger.messages.outgoing.runtime;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.program.DebuggerImpl;

/**
 * Message used to signal the continuation of the source code execution. Message originates from the
 * {@link DebuggerImpl} and is sent to the runtime.
 * 
 * @author Thomas Ulrich
 *
 */
public class NextStepRequestedMessage extends AbstractMessage {

    /**
     * Initializes a new instance of the {@link NextStepRequestedMessage}.
     * 
     * @param message
     *            String representing the 'the view has requested to step to the next instruction' message.
     */
    public NextStepRequestedMessage(String message) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return OutgoingMessageType.NEXTSTEPREQUESTED.toString();
    }
}
