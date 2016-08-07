package de.cau.lps.debugger.messages.outgoing.runtimetest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.runtime.NextStepRequestedMessage;

/**
 * Tests the {@link NextStepRequestedMessage} class.
 */
public class NextStepRequestedMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = OutgoingMessageType.NEXTSTEPREQUESTED.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        NextStepRequestedMessage next = new NextStepRequestedMessage(OutgoingMessageType.NEXTSTEPREQUESTED.toString());
        Assert.assertNotNull(next);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        NextStepRequestedMessage next = new NextStepRequestedMessage(OutgoingMessageType.NEXTSTEPREQUESTED.toString());
        Assert.assertEquals(message, next.toString());
    }

}
