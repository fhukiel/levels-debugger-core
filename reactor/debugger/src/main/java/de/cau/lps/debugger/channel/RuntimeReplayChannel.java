package de.cau.lps.debugger.channel;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.outgoing.runtime.NextStepRequestedMessage;
import de.cau.lps.debugger.replay.RuntimeReplayListener;

/**
 * A {@link CommunicationChannel} implementation used to replay recorded messages.
 * 
 * @author Thomas Ulrich
 */
public class RuntimeReplayChannel implements CommunicationChannel {
    private static Logger logger = LoggerFactory.getLogger(RuntimeReplayChannel.class);

    private Object synchronizer;

    /**
     * Initializes a new instance of the {@link RuntimeReplayChannel} class.
     * 
     * @param synchronizer
     *            An {@link Object} used to synchronize with a {@link RuntimeReplayListener} object.
     */
    public RuntimeReplayChannel(Object synchronizer) {
        this.synchronizer = synchronizer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.channel.CommunicationChannel#sendMessage(de.cau.lps.debugger.messages.AbstractMessage)
     */
    @Override
    public void sendMessage(AbstractMessage message) throws IOException {
        logger.debug("Received a message, checking if it's a message I can handle.");
        if (message instanceof NextStepRequestedMessage) {
            logger.debug("It's a message I can handle. Trying to notify all threads waiting on lock.");
            synchronized (this.synchronizer) {
                logger.debug("I'm in the synchronized block, sending notify.");
                this.synchronizer.notifyAll();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.channel.CommunicationChannel#receiveMessage()
     */
    @Override
    public Optional<String> receiveMessage() {
        return Optional.empty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.channel.CommunicationChannel#open()
     */
    @Override
    public void open() throws Exception {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.channel.CommunicationChannel#getTarget()
     */
    @Override
    public CommunicationChannelTarget getTarget() {
        return CommunicationChannelTarget.RUNTIME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.channel.CommunicationChannel#isAvailable()
     */
    @Override
    public boolean isAvailable() {
        return true;
    }
}
