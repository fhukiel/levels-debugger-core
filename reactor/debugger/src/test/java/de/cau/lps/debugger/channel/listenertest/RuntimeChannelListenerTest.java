package de.cau.lps.debugger.channel.listenertest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.channel.listener.RuntimeChannelListener;
import de.cau.lps.debugger.channel.listenertest.mocks.RuntimeMessageHandlerMock;
import de.cau.lps.debugger.channel.provider.CommunicationChannelProvider;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.runtime.UpdatePositionMessage;

/**
 * Tests the runtime channel listener.
 */
public class RuntimeChannelListenerTest {

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        CountDownLatch latch = new CountDownLatch(1);
        try (CommunicationChannel channel = CommunicationChannelProvider.getRuntimeChannel(true)) {
            RuntimeChannelListener listener = new RuntimeChannelListener(channel, new RuntimeMessageHandlerMock(),
                latch);
            Assert.assertNotNull(listener);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the run method.
     */
    @Test
    public void run() {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            List<AbstractMessage> tape = Arrays.asList(new UpdatePositionMessage(
                IncomingMessageType.UPDATEPOSITION + MessageFormatter.DELIMITER + 1 + MessageFormatter.DELIMITER + 2));
            try (CommunicationChannel channel = CommunicationChannelProvider.getRuntimeChannel(tape, true)) {
                RuntimeChannelListener listener = new RuntimeChannelListener(channel, new RuntimeMessageHandlerMock(),
                    latch);
                Thread thread = new Thread(listener);
                thread.start();
                thread.join();
                Assert.assertEquals(0, latch.getCount());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                Assert.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
