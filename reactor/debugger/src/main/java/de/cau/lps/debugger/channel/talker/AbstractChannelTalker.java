package de.cau.lps.debugger.channel.talker;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.messages.AbstractMessage;

/**
 * This class serves as the base class for Channel talkers, i.e. objects that can send messages to
 * {@link CommunicationChannel}s.
 * 
 * @author Thomas Ulrich
 */
public abstract class AbstractChannelTalker {
    private static Logger logger = LoggerFactory.getLogger(AbstractChannelTalker.class);
    private CommunicationChannel channel;
    private boolean discardAll;

    /**
     * Initializes a new instance of the {@link AbstractChannelTalker} class.
     * 
     * @param channel
     *            A {@link CommunicationChannel} object to send messages to.
     */
    public AbstractChannelTalker(CommunicationChannel channel) {
        this.channel = channel;
        this.discardAll = false;
    }

    /**
     * Sends a message to the channel.
     * 
     * @param message
     *            The message to send.
     * @throws IOException
     *             Thrown if an exception occurs while sending.
     */
    protected void sendMessage(AbstractMessage message) throws IOException {
        if (!this.discardAll) {
            try {
                this.channel.sendMessage(message);
            } catch (IOException e) {
                logger.error("An error occured while sending message to view, exception was " + e.getMessage(), e);
                throw e;
            }
        }
    }

    /**
     * Gets the discardAll flag.
     * 
     * @return True if all messages are discarded without being sent to the channel, otherwise false.
     */
    public boolean isDiscardAll() {
        return discardAll;
    }

    /**
     * Sets the discardAll flag.
     * 
     * @param discardAll
     *            True if all messages should be discarded without being sent to the channel, otherwise false.
     */
    public void setDiscardAll(boolean discardAll) {
        this.discardAll = discardAll;
    }
}
