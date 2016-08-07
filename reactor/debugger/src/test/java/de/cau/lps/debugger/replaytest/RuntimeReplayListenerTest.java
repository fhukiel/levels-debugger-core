package de.cau.lps.debugger.replaytest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.channel.listenertest.mocks.RuntimeMessageHandlerMock;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.runtime.UpdatePositionMessage;
import de.cau.lps.debugger.messages.incoming.runtime.UpdateTableMessage;
import de.cau.lps.debugger.replay.RuntimeReplayListener;
import de.cau.lps.debugger.replay.MessageRecorder;

/**
 * Tests the {@link RuntimeReplayListener} class.
 * 
 * @author Thomas Ulrich
 */
public class RuntimeReplayListenerTest {
    private static Logger logger = LoggerFactory.getLogger(RuntimeReplayListenerTest.class);

    private static MessageRecorder messageRecorder;

    /**
     * Initializes required objects.
     */
    @Before
    public void initialize() {
        try {
            messageRecorder = new MessageRecorder();
            UpdatePositionMessage posMsg = new UpdatePositionMessage(
                IncomingMessageType.UPDATEPOSITION + MessageFormatter.DELIMITER + 1 + MessageFormatter.DELIMITER + 2);
            messageRecorder.record(posMsg);
            UpdateTableMessage tableMsg = new UpdateTableMessage(
                IncomingMessageType.UPDATETABLE + MessageFormatter.DELIMITER + "a" + MessageFormatter.DELIMITER
                    + "value" + MessageFormatter.DELIMITER + "0x0001");
            messageRecorder.record(tableMsg);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        Object synchronizer = new Object();
        RuntimeMessageHandlerMock handler = new RuntimeMessageHandlerMock();
        RuntimeReplayListener listener = new RuntimeReplayListener(messageRecorder, handler, synchronizer);
        Assert.assertNotNull(listener);
    }

    /**
     * Tests the run method.
     */
    @Test
    public void run() {
        Object synchronizer = new Object();
        RuntimeMessageHandlerMock handler = new RuntimeMessageHandlerMock();
        RuntimeReplayListener listener = new RuntimeReplayListener(messageRecorder, handler, synchronizer);
        Thread thread = new Thread(listener);
        thread.start();

        while (!listener.isDone()) {
            if (!handler.getReceivedMessages().isEmpty()) {
                logger.debug("List of messages is not empty.");
                AbstractMessage msg = handler.getReceivedMessages().remove();
                if (msg.isRequiresAcknowledge()) {
                    logger.debug("Message requires acknowledge, trying to enter synchronized block and send notify.");
                    synchronized (synchronizer) {
                        synchronizer.notifyAll();
                        logger.debug("Notify sent.");
                    }
                } else {
                    logger.debug("Message does not require acknowledge.");
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                logger.error("An error occurred while testing run method: " + e.getMessage(), e);
                e.printStackTrace();
            }
        }
    }
}
