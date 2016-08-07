package de.cau.lps.debugger.messages.incoming.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.RemoveAllBreakpointsMessage;

/**
 * Tests the {@link RemoveAllBreakpointsMessage}.
 */
public class RemoveAllBreakpointsMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = IncomingMessageType.REMOVEALLBREAKPOINTS.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        RemoveAllBreakpointsMessage run = new RemoveAllBreakpointsMessage(message);
        Assert.assertNotNull(run);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        RemoveAllBreakpointsMessage run = new RemoveAllBreakpointsMessage(message);
        Assert.assertEquals(message, run.toString());
    }
}
