package de.cau.lps.debugger.messages.outgoing.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.view.ReadyMessage;

/**
 * Tests the {@link ReadyMessage} class.
 */
public class ReadyMessageTest {

    private static String representation;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        representation = OutgoingMessageType.READY.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        ReadyMessage msg = new ReadyMessage(OutgoingMessageType.READY.toString());
        Assert.assertNotNull(msg);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        ReadyMessage msg = new ReadyMessage(OutgoingMessageType.READY.toString());
        Assert.assertEquals(representation, msg.toString());
    }
}
