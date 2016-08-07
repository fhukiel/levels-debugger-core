package de.cau.lps.debugger.messages.incoming.viewtest;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.StartReplayMessage;
import de.cau.lps.debugger.messages.incoming.view.StepMessage;

/**
 * Tests the {@link StepMessage} class.
 */
public class StartReplayMessageTest {

    private static String message;
    private static String brokenMessage;
    private static String callID;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        callID = UUID.randomUUID().toString();
        message = IncomingMessageType.STARTREPLAY.toString() + MessageFormatter.DELIMITER + callID;
        brokenMessage = IncomingMessageType.STARTREPLAY.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        StartReplayMessage start;
        try {
            start = new StartReplayMessage(message);
            Assert.assertNotNull(start);
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        try {
            StartReplayMessage start = new StartReplayMessage(message);
            Assert.assertEquals(message, start.toString());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the extraction of the call id from the message.
     */
    @Test
    public void getCallId() {
        try {
            StartReplayMessage start = new StartReplayMessage(message);
            Assert.assertEquals(callID, start.getReplayFromCallId());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tries to construct a {@link StartReplayMessage} from a falsely formatted string.
     */
    @SuppressWarnings("unused")
    @Test
    public void tryBrokenMessage() {
        try {
            new StartReplayMessage(brokenMessage);
            Assert.fail();
        } catch (MessageWronglyFormattedException e) {
            return;
        }
        Assert.fail();
    }
}
