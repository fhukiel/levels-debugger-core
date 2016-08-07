package de.cau.lps.debugger.channel.listenertest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.channel.listener.ViewChannelListener;
import de.cau.lps.debugger.channel.listenertest.mocks.ViewMessageHandlerMock;
import de.cau.lps.debugger.channel.provider.CommunicationChannelProvider;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.StepMessage;

/**
 * Tests the view channel listener.
 */
public class ViewChannelListenerTest {

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        CountDownLatch latch = new CountDownLatch(1);
        try (CommunicationChannel channel = CommunicationChannelProvider.getViewChannel(true)) {
            ViewChannelListener manager = new ViewChannelListener(channel, new ViewMessageHandlerMock(), latch);
            Assert.assertNotNull(manager);
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
            List<AbstractMessage> tape = Arrays.asList(new StepMessage(IncomingMessageType.STEP.toString()));
            try (CommunicationChannel channel = CommunicationChannelProvider.getViewChannel(tape, true)) {
                ViewChannelListener listener = new ViewChannelListener(channel, new ViewMessageHandlerMock(), latch);
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
