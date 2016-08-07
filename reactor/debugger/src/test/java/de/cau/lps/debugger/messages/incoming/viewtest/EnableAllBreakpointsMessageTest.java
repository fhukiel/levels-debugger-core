package de.cau.lps.debugger.messages.incoming.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.EnableAllBreakpointsMessage;

/**
 * Tests the {@link EnableAllBreakpointsMessage}.
 */
public class EnableAllBreakpointsMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = IncomingMessageType.ENABLEALLBREAKPOINTS.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        EnableAllBreakpointsMessage run = new EnableAllBreakpointsMessage(message);
        Assert.assertNotNull(run);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        EnableAllBreakpointsMessage run = new EnableAllBreakpointsMessage(message);
        Assert.assertEquals(message, run.toString());
    }
}
