package de.cau.lps.debugger.messages.outgoing.view;

import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.program.DebuggerImpl;

/**
 * Message used to signal the update of the current source code position. Message originates from the
 * {@link DebuggerImpl} and is sent to the view.
 * 
 * @author Thomas Ulrich
 *
 */
public class PositionUpdatedMessage extends AbstractMessage {

    private Position position;

    /**
     * Initializes a new instance of the {@link PositionUpdatedMessage} class.
     * 
     * @param position
     *            The current position.
     */
    public PositionUpdatedMessage(Position position) {
        this.position = new Position(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append(OutgoingMessageType.POSITIONUPDATED);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(this.position.getLine());
        builder.append(MessageFormatter.DELIMITER);
        builder.append(this.position.getRow());
        return builder.toString();
    }
}
