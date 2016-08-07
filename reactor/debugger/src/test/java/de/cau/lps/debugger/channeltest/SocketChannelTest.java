package de.cau.lps.debugger.channeltest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import de.cau.lps.debugger.channel.ChannelInitializer;
import de.cau.lps.debugger.channel.CommunicationChannelTarget;
import de.cau.lps.debugger.channel.SocketChannel;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.StepMessage;

/**
 * Tests the {@link SocketChannel} class.
 */
public class SocketChannelTest {

    private static int port = 59599;

    /**
     * Tests the constructor.
     */
    @SuppressWarnings("resource")
    @Test
    public void ctor() {
        Assert.assertNotNull(new SocketChannel(port, CommunicationChannelTarget.VIEW));
    }

    /**
     * Tests the open method.
     */
    @Test
    public void open() {
        try (SocketChannel chan = new SocketChannel(port, CommunicationChannelTarget.VIEW)) {
            openChannel(chan);
            chan.close();
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
        try (SocketChannel chan = new SocketChannel(port, CommunicationChannelTarget.VIEW)) {
            openChannel(chan);
            chan.sendMessage(new StepMessage(IncomingMessageType.STEP.toString()));
            chan.close();
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
        try (SocketChannel chan = new SocketChannel(port, CommunicationChannelTarget.VIEW)) {
            String msg = "Testmessage";
            try (Socket socket = openChannel(chan)) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write(msg);
                writer.newLine();
                writer.flush();
                Optional<String> received = chan.receiveMessage();
                Assert.assertTrue(received.isPresent());
                Assert.assertEquals(msg, received.get());
                chan.close();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private Socket openChannel(SocketChannel chan) throws UnknownHostException, IOException, InterruptedException {
        ChannelInitializer init = new ChannelInitializer(chan);
        Thread thread = new Thread(init);
        thread.start();
        Socket socket = new Socket("localhost", 59599);
        thread.join();
        Assert.assertTrue(chan.isAvailable());
        return socket;
    }
}
