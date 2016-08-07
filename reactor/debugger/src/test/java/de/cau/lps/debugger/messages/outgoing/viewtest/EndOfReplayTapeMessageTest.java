package de.cau.lps.debugger.messages.outgoing.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.view.EndOfReplayTapeMessage;

/**
 * Tests the {@link EndOfReplayTapeMessage} class.
 */
public class EndOfReplayTapeMessageTest {

    private static String representation;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        representation = OutgoingMessageType.ENDOFREPLAYTAPE.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        EndOfReplayTapeMessage msg = new EndOfReplayTapeMessage(OutgoingMessageType.ENDOFREPLAYTAPE.toString());
        Assert.assertNotNull(msg);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        EndOfReplayTapeMessage msg = new EndOfReplayTapeMessage(OutgoingMessageType.ENDOFREPLAYTAPE.toString());
        Assert.assertEquals(representation, msg.toString());
    }
}
