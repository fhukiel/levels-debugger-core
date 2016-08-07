package de.cau.lps.debugger.messages.incoming.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.StepMessage;
import de.cau.lps.debugger.messages.incoming.view.StopReplayMessage;

/**
 * Tests the {@link StepMessage} class.
 */
public class StopReplayMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = IncomingMessageType.STOPREPLAY.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        StopReplayMessage stop = new StopReplayMessage(message);
        Assert.assertNotNull(stop);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        StopReplayMessage stop = new StopReplayMessage(message);
        Assert.assertEquals(message, stop.toString());
    }
}
