package de.cau.lps.debugger.messages.incoming.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.DisableAllBreakpointsMessage;

/**
 * Tests the {@link DisableAllBreakpointsMessageTest}.
 */
public class DisableAllBreakpointsMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = IncomingMessageType.DISABLEALLBREAKPOINTS.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        DisableAllBreakpointsMessage run = new DisableAllBreakpointsMessage(message);
        Assert.assertNotNull(run);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        DisableAllBreakpointsMessage run = new DisableAllBreakpointsMessage(message);
        Assert.assertEquals(message, run.toString());
    }
}
