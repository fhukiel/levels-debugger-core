package de.cau.lps.debugger.messages.outgoing.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.view.AutoSteppingDisabledMessage;

/**
 * Tests the {@link AutoSteppingDisabledMessage} class.
 */
public class AutoSteppingDisabledMessageTest {

    private static String representation;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        representation = OutgoingMessageType.AUTOSTEPPINGDISABLED.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        AutoSteppingDisabledMessage msg = new AutoSteppingDisabledMessage(
            OutgoingMessageType.AUTOSTEPPINGDISABLED.toString());
        Assert.assertNotNull(msg);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        AutoSteppingDisabledMessage msg = new AutoSteppingDisabledMessage(
            OutgoingMessageType.AUTOSTEPPINGDISABLED.toString());
        Assert.assertEquals(representation, msg.toString());
    }
}
