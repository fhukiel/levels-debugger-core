package de.cau.lps.debugger.messages.incoming.runtimetest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.runtime.UpdatePositionMessage;

/**
 * Tests the {@link UpdatePositionMessage} class.
 */
public class UpdatePositionMessageTest {

    private static String message;
    private static String brokenMessage;
    private static int line = 2;
    private static int row = 3;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {

        StringBuilder builder = new StringBuilder();
        builder.append(IncomingMessageType.UPDATEPOSITION);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(line);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(row);
        message = builder.toString();
        brokenMessage = "xxx;lala;";
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        UpdatePositionMessage posMes;
        try {
            posMes = new UpdatePositionMessage(message);
            Assert.assertNotNull(posMes);
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the position getter.
     */
    @Test
    public void getPosition() {
        UpdatePositionMessage posMes;
        try {
            posMes = new UpdatePositionMessage(message);
            Assert.assertEquals(line, posMes.getPosition().getLine());
            Assert.assertEquals(row, posMes.getPosition().getRow());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        UpdatePositionMessage posMes;
        try {
            posMes = new UpdatePositionMessage(message);
            Assert.assertEquals(message, posMes.toString());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tries to create an {@link UpdatePositionMessage} from a falsely formatted string.
     */
    @SuppressWarnings("unused")
    @Test
    public void tryBrokenMessage() {
        try {
            new UpdatePositionMessage(brokenMessage);
            Assert.fail();
        } catch (MessageWronglyFormattedException e) {
            return;
        }

        Assert.fail();
    }

    /**
     * Tests the setting of the requiresAcknowledge flag.
     */
    @Test
    public void requiresAcknowledge() {
        try {
            UpdatePositionMessage posMes = new UpdatePositionMessage(message);
            Assert.assertTrue(posMes.isRequiresAcknowledge());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
