package de.cau.lps.debugger.channel.listener;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.channel.CommunicationChannel;

/**
 * Listens to a {@link CommunicationChannel} and sends received messages to a Handler via a defined callback interface.
 * 
 * @author Thomas Ulrich
 *
 */
public abstract class AbstractChannelListener implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(AbstractChannelListener.class);
    protected CountDownLatch latch;
    protected CommunicationChannel channel;

    /**
     * Handles a received message.
     * 
     * @param receivedMessage
     *            The received message.
     */
    protected abstract void handleMessage(String receivedMessage);

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        boolean loop = true;
        while (loop) {
            Optional<String> received = this.channel.receiveMessage();
            if (received.isPresent()) {
                this.handleMessage(received.get());
            }
            loop = received.isPresent();
        }
        logger.debug("AbstractChannelListener for " + this.channel.getTarget() + " channel is terminating.");
        this.shutdown();
    }

    /**
     * Shuts the channel down.
     */
    private void shutdown() {
        this.latch.countDown();
    }
}
