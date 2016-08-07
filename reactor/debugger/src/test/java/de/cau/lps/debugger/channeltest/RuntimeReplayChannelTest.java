package de.cau.lps.debugger.channeltest;

import org.junit.Assert;
import org.junit.Test;

import de.cau.lps.debugger.channel.RuntimeReplayChannel;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.runtime.NextStepRequestedMessage;

/**
 * Tests the {@link RuntimeReplayChannel} class.
 */
public class RuntimeReplayChannelTest {

    /**
     * Tests the constructor.
     */
    @SuppressWarnings("resource")
    @Test
    public void ctor() {
        Object synchronizer = new Object();
        Assert.assertNotNull(new RuntimeReplayChannel(synchronizer));
    }

    /**
     * Tests the open method.
     */
    @Test
    public void open() {
        Object synchronizer = new Object();
        try (RuntimeReplayChannel channel = new RuntimeReplayChannel(synchronizer)) {
            channel.open();
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the sendMessage method.
     */
    @Test
    public void sendMessage() {
        Object synchronizer = new Object();
        NextStepRequestedMessage msg = new NextStepRequestedMessage(OutgoingMessageType.NEXTSTEPREQUESTED.toString());
        try (RuntimeReplayChannel channel = new RuntimeReplayChannel(synchronizer)) {
            channel.open();
            synchronized (synchronizer) {
                new Thread(() -> {
                    try {
                        channel.sendMessage(msg);
                    } catch (Exception e) {
                        Assert.fail();
                        e.printStackTrace();
                    }
                }).start();
                synchronizer.wait();
                Assert.assertTrue(true);
            }
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the receiveMessage method.
     */
    @Test
    public void receiveMessage() {
        try (RuntimeReplayChannel channel = new RuntimeReplayChannel(new Object())) {
            Assert.assertFalse(channel.receiveMessage().isPresent());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
