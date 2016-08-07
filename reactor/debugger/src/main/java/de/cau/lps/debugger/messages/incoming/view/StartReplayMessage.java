package de.cau.lps.debugger.messages.incoming.view;

import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal the start of a replay from a certain point in time forward.
 */
public class StartReplayMessage extends AbstractMessage {

    private String expectedFormat;
    private String replayFromCallId;

    /**
     * Initializes a new instance of the {@link StartReplayMessage} class.
     * 
     * @param message
     *            The String representing the 'replay from here' message.
     * @throws MessageWronglyFormattedException
     *             Thrown if the String message is wrongly formatted.
     */
    public StartReplayMessage(String message) throws MessageWronglyFormattedException {
        StringBuilder builder = new StringBuilder();
        builder.append(IncomingMessageType.STARTREPLAY.toString());
        builder.append(MessageFormatter.DELIMITER);
        builder.append("<Call ID>");
        this.expectedFormat = builder.toString();

        String[] splitted = message.split(MessageFormatter.DELIMITER);
        if (splitted.length != 2) {
            throw new MessageWronglyFormattedException(message, expectedFormat);
        }
        this.replayFromCallId = splitted[1];
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        return IncomingMessageType.STARTREPLAY.toString() + MessageFormatter.DELIMITER + this.replayFromCallId;
    }

    /**
     * Gets the ID of the call from which to replay.
     * 
     * @return The {@link Call} ID.
     */
    public String getReplayFromCallId() {
        return replayFromCallId;
    }
}
