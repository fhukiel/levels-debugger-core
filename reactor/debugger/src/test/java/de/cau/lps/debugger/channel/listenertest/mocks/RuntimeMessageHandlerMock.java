package de.cau.lps.debugger.channel.listenertest.mocks;

import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.handler.RuntimeMessageHandler;

/**
 * Mocks out a {@link RuntimeMessageHandler} for testing purposes.
 */
public class RuntimeMessageHandlerMock implements RuntimeMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(RuntimeMessageHandlerMock.class);

    private Queue<AbstractMessage> receivedMessages;

    /**
     * Initializes a new instance of the {@link RuntimeMessageHandlerMock} class.
     */
    public RuntimeMessageHandlerMock() {
        this.receivedMessages = new LinkedList<AbstractMessage>();
    }

    @Override
    public void handleIncomingRuntimeMessage(AbstractMessage message) {
        logger.debug("Received message " + message.toString());
        this.receivedMessages.add(message);
    }

    /**
     * Gets a queue of received messages.
     * 
     * @return A Queue of {@link AbstractMessage}s.
     */
    public Queue<AbstractMessage> getReceivedMessages() {
        return receivedMessages;
    }
}