package de.cau.lps.debugger.channel.listener;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.exception.MessageConversionException;
import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.exception.UnknownMessageTypeException;
import de.cau.lps.debugger.messages.handler.RuntimeMessageHandler;
import de.cau.lps.debugger.messages.incoming.IncomingMessageConverter;

/**
 * Listens to a runtime {@link CommunicationChannel} and calls a {@link RuntimeMessageHandler} whenever a message is
 * received.
 * 
 * @author Thomas Ulrich
 *
 */
public class RuntimeChannelListener extends AbstractChannelListener {
    private static Logger logger = LoggerFactory.getLogger(RuntimeChannelListener.class);
    private RuntimeMessageHandler handler;

    /**
     * Initializes a new instance of the {@link RuntimeMessageHandler} meChannelListener class.
     * 
     * @param channel
     *            The {@link CommunicationChannel} to listen to.
     * @param handler
     *            The {@link RuntimeMessageHandler} to inform whenever a message is received.
     * @param latch
     *            A {@link CountDownLatch} used to synchronize communication with the calling object.
     */
    public RuntimeChannelListener(CommunicationChannel channel, RuntimeMessageHandler handler, CountDownLatch latch) {
        this.channel = channel;
        this.handler = handler;
        this.latch = latch;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.channel.listener.AbstractChannelListener#handleMessage(java.lang.String)
     */
    @Override
    protected void handleMessage(String receivedMessage) {
        try {
            this.handler.handleIncomingRuntimeMessage(IncomingMessageConverter.convert(receivedMessage));
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            logger.error("An error occurred while handling incoming message, exception was " + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
