package de.cau.lps.debugger.messages.incoming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.MessageFormatter;

/**
 * Base class for any message that deals with positional information.
 * 
 * @author Thomas Ulrich
 *
 */
public class PositionalMessage extends AbstractMessage {

    private static Logger logger = LoggerFactory.getLogger(PositionalMessage.class);
    private Position position;
    private IncomingMessageType type;
    private String expectedFormat;

    /**
     * Initializes a new instance of the {@link PositionalMessage} class.
     * 
     * @param message
     *            The Message string for this message object.
     * @param type
     *            The MessageType. Is required since PositionalMessage is base class for multiple message types.
     * @throws MessageWronglyFormattedException
     *             Thrown if message String is wrongly formatted.
     */
    public PositionalMessage(String message, IncomingMessageType type) throws MessageWronglyFormattedException {

        StringBuilder builder = new StringBuilder();
        builder.append("<MessageType>");
        builder.append(MessageFormatter.DELIMITER);
        builder.append("<LineNumber>");
        builder.append(MessageFormatter.DELIMITER);
        builder.append("<RowNumber>");
        this.expectedFormat = builder.toString();

        this.type = type;
        try {
            String[] splitted = message.split(MessageFormatter.DELIMITER);
            if (splitted.length != 3) {
                throw new MessageWronglyFormattedException(message, this.expectedFormat);
            }

            int line = Integer.valueOf(splitted[1]);
            int row = Integer.valueOf(splitted[2]);
            this.position = new Position(line, row);

        } catch (NumberFormatException e) {
            logger.error("Could not create message, exception was " + e.getMessage(), e);
            throw new MessageWronglyFormattedException(message, this.expectedFormat);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.type);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(this.getPosition().getLine());
        builder.append(MessageFormatter.DELIMITER);
        builder.append(this.getPosition().getRow());

        return builder.toString();
    }

    /**
     * Gets the {@link Position} passed to this message.
     * 
     * @return The Position object.
     */
    public Position getPosition() {
        return position;
    }
}
