package de.cau.lps.debugger.channel.listener;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.exception.MessageConversionException;
import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.exception.UnknownMessageTypeException;
import de.cau.lps.debugger.messages.handler.ViewMessageHandler;
import de.cau.lps.debugger.messages.incoming.IncomingMessageConverter;

/**
 * Listens to a view {@link CommunicationChannel} and calls a {@link ViewMessageHandler} whenever a message is received.
 * 
 * @author Thomas Ulrich
 *
 */
public class ViewChannelListener extends AbstractChannelListener {
    private static Logger logger = LoggerFactory.getLogger(ViewChannelListener.class);
    private ViewMessageHandler handler;

    /**
     * Initializes a new instance of the {@link ViewChannelListener} class.
     * 
     * @param channel
     *            The {@link CommunicationChannel}Channel to listen to.
     * @param handler
     *            The {@link ViewMessageHandler} to inform whenever a message is received.
     * 
     * @param latch
     *            A {@link CountDownLatch} used to synchronize communication with the calling object.
     */
    public ViewChannelListener(CommunicationChannel channel, ViewMessageHandler handler, CountDownLatch latch) {
        this.channel = channel;
        this.handler = handler;
        this.latch = latch;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.channel.listener.AbstractChannelListener# handleMessage(java.lang.String)
     */
    @Override
    protected void handleMessage(String receivedMessage) {
        try {
            this.handler.handleIncomingViewMessage(IncomingMessageConverter.convert(receivedMessage));
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            logger.error("An error occurred while handling incoming message, exception was " + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
