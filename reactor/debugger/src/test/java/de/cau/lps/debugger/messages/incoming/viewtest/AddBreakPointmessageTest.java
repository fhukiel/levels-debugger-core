package de.cau.lps.debugger.messages.incoming.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.AddBreakpointMessage;

/**
 * Tests the {@link AddBreakpointMessage} class.
 */
public class AddBreakPointmessageTest {

    private static String message;
    private static String brokenMessage;
    private static int line = 2;
    private static int row = 3;

    /**
     * Sets up objects for the tests.
     */
    @Before
    public void initialize() {
        StringBuilder builder = new StringBuilder();
        builder.append(IncomingMessageType.ADDBREAKPOINT);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(line);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(row);
        message = builder.toString();
        brokenMessage = "xxx;yyy;zzz;aaa";
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        AddBreakpointMessage breakpointMessage;
        try {
            breakpointMessage = new AddBreakpointMessage(message);
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
        AddBreakpointMessage breakpointMessage;
        try {
            breakpointMessage = new AddBreakpointMessage(message);
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
        AddBreakpointMessage breakpointMessage;
        try {
            breakpointMessage = new AddBreakpointMessage(message);
            Assert.assertEquals(message, breakpointMessage.toString());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tries to create an instance of this class using a falsely formatted string.
     */
    @SuppressWarnings("unused")
    @Test
    public void tryBrokenMessage() {
        try {
            new AddBreakpointMessage(brokenMessage);
            Assert.fail();
        } catch (MessageWronglyFormattedException e) {
            return;
        }
        Assert.fail();
    }
}
