package de.cau.lps.debugger.messages.outgoing.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.view.AutoSteppingEnabledMessage;

/**
 * Tests the {@link AutoSteppingEnabledMessage} class.
 */
public class AutoSteppingEnabledMessageTest {

    private static String representation;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        representation = OutgoingMessageType.AUTOSTEPPINGENABLED.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        AutoSteppingEnabledMessage msg = new AutoSteppingEnabledMessage(
            OutgoingMessageType.AUTOSTEPPINGENABLED.toString());
        Assert.assertNotNull(msg);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        AutoSteppingEnabledMessage msg = new AutoSteppingEnabledMessage(
            OutgoingMessageType.AUTOSTEPPINGENABLED.toString());
        Assert.assertEquals(representation, msg.toString());
    }
}
