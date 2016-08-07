package de.cau.lps.debugger.messages.incoming.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.StepOverMessage;

/**
 * Tests the {@link StepOverMessage} class.
 */
public class StepOverMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = IncomingMessageType.STEPOVER.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        StepOverMessage so = new StepOverMessage(message);
        Assert.assertNotNull(so);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        StepOverMessage so = new StepOverMessage(message);
        Assert.assertEquals(message, so.toString());
    }
}
