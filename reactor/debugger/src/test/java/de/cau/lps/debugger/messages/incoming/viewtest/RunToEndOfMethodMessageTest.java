package de.cau.lps.debugger.messages.incoming.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.EnableAllBreakpointsMessage;
import de.cau.lps.debugger.messages.incoming.view.RunToEndOfMethodMessage;

/**
 * Tests the {@link EnableAllBreakpointsMessage}.
 */
public class RunToEndOfMethodMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = IncomingMessageType.RUNTOENDOFMETHOD.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        RunToEndOfMethodMessage run = new RunToEndOfMethodMessage(message);
        Assert.assertNotNull(run);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        RunToEndOfMethodMessage run = new RunToEndOfMethodMessage(message);
        Assert.assertEquals(message, run.toString());
    }
}
