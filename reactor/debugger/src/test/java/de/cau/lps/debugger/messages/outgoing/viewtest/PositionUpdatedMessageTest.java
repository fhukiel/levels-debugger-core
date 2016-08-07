package de.cau.lps.debugger.messages.outgoing.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.view.PositionUpdatedMessage;

/**
 * Tests the {@link PositionUpdatedMessage} class.
 */
public class PositionUpdatedMessageTest {

    private static int line;
    private static int row;
    private static Position position;
    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        position = new Position(line, row);
        StringBuilder builder = new StringBuilder();
        builder.append(OutgoingMessageType.POSITIONUPDATED);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(line);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(row);
        message = builder.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        PositionUpdatedMessage updated = new PositionUpdatedMessage(position);
        Assert.assertNotNull(updated);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        PositionUpdatedMessage updated = new PositionUpdatedMessage(position);
        Assert.assertEquals(message, updated.toString());
    }
}
