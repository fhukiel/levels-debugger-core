package de.cau.lps.debugger.messages.incoming.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.RemoveBreakpointMessage;

/**
 * Tests the {@link RemoveBreakpointMessage}.
 */
public class RemoveBreakpointMessageTest {

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
        builder.append(IncomingMessageType.REMOVEBREAKPOINT);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(line);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(row);
        message = builder.toString();

        brokenMessage = "xxxx;452";
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        RemoveBreakpointMessage breakpointMessage;
        try {
            breakpointMessage = new RemoveBreakpointMessage(message);
            Assert.assertNotNull(breakpointMessage);
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
        RemoveBreakpointMessage breakpointMessage;
        try {
            breakpointMessage = new RemoveBreakpointMessage(message);
            Assert.assertEquals(line, breakpointMessage.getPosition().getLine());
            Assert.assertEquals(row, breakpointMessage.getPosition().getRow());
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
        RemoveBreakpointMessage breakpointMessage;
        try {
            breakpointMessage = new RemoveBreakpointMessage(message);
            Assert.assertEquals(message, breakpointMessage.toString());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tries to create a {@link RemoveBreakpointMessage} from a falsely formatted string.
     */
    @SuppressWarnings("unused")
    @Test
    public void tryBrokenMessage() {
        try {
            new RemoveBreakpointMessage(brokenMessage);
            Assert.fail();
        } catch (MessageWronglyFormattedException e) {
            return;
        }
        Assert.fail();
    }
}
