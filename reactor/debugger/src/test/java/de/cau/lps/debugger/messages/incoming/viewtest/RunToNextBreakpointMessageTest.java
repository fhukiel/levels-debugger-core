package de.cau.lps.debugger.messages.incoming.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.RunToNextBreakpointMessage;

/**
 * Tests the {@link RunToNextBreakpointMessage}.
 */
public class RunToNextBreakpointMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = IncomingMessageType.RUNTONEXTBREAKPOINT.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        RunToNextBreakpointMessage run = new RunToNextBreakpointMessage(message);
        Assert.assertNotNull(run);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        RunToNextBreakpointMessage run = new RunToNextBreakpointMessage(message);
        Assert.assertEquals(message, run.toString());
    }
}
