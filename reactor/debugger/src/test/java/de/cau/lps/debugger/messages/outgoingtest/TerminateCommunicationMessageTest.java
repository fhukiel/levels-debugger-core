package de.cau.lps.debugger.messages.outgoingtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.TerminateCommunicationMessage;
import de.cau.lps.debugger.messages.outgoing.runtime.NextStepRequestedMessage;

/**
 * Tests the {@link NextStepRequestedMessage} class.
 */
public class TerminateCommunicationMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = OutgoingMessageType.TERMINATECOMMUNICATION.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        TerminateCommunicationMessage terminate = new TerminateCommunicationMessage(
            OutgoingMessageType.TERMINATECOMMUNICATION.toString());
        Assert.assertNotNull(terminate);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        TerminateCommunicationMessage terminate = new TerminateCommunicationMessage(
            OutgoingMessageType.TERMINATECOMMUNICATION.toString());
        Assert.assertEquals(message, terminate.toString());
    }

}
