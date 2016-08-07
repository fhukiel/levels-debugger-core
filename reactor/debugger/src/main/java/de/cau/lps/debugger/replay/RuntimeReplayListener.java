package de.cau.lps.debugger.replay;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.channel.RuntimeReplayChannel;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.handler.RuntimeMessageHandler;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.runtime.EndOfTapeMessage;

/**
 * Listens to a {@link RuntimeReplayChannel} and returns the recorded messages.
 * 
 * @author Thomas Ulrich
 */
public class RuntimeReplayListener implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(RuntimeReplayListener.class);

    private MessageRecorder messageRecorder;
    private RuntimeMessageHandler handler;
    private Object synchronizer;
    private boolean done;
    private boolean endOfTape;

    /**
     * Initializes a new instance of the {@link RuntimeReplayListener} class.
     * 
     * @param messageRecorder
     *            A {@link MessageRecorder} containing the replay information.
     * @param handler
     *            A {@link RuntimeMessageHandler} to send the messages to.
     * @param synchronizer
     *            An {@link Object} to synchronize with a {@link RuntimeReplayChannel}.
     */
    public RuntimeReplayListener(MessageRecorder messageRecorder, RuntimeMessageHandler handler, Object synchronizer) {
        this.messageRecorder = messageRecorder;
        this.handler = handler;
        this.synchronizer = synchronizer;
        this.done = false;
        this.endOfTape = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        logger.debug("RuntimeReplayListener up and running.");
        Optional<AbstractMessage> maybeMessage = this.playTape();
        while (maybeMessage.isPresent()) {
            logger.debug("RuntimeReplayListener has message.");
            AbstractMessage message = maybeMessage.get();
            try {
                synchronized (this.synchronizer) {
                    logger.debug("RuntimeReplayListener sends message to handler.");
                    this.handleMessageAsynchronously(message);
                    this.acknowledgeMessage(message);
                }
            } catch (Exception e) {
                logger.error("An error occurred while replay messages, exception was " + e.getMessage(), e);
                e.printStackTrace();
                break;
            }
            maybeMessage = this.playTape();
        }
        this.done = true;
        logger.debug("RuntimeReplayListener has no more messages to replay.");
    }

    /**
     * Creates a new thread to handle the message.
     * 
     * @param message
     *            The message to be handled.
     */
    private void handleMessageAsynchronously(AbstractMessage message) {
        Thread handlerThread = new Thread(() -> {
            try {
                this.handler.handleIncomingRuntimeMessage(message);
            } catch (Exception e) {
                logger.error("An error occurred while replaying messages, exception was " + e.getMessage());
                e.printStackTrace();
            }
        });
        handlerThread.start();
    }

    private void acknowledgeMessage(AbstractMessage message) {
        synchronized (this.synchronizer) {
            logger.debug("Checking if current message requires acknowledge.");
            try {
                while (message.isRequiresAcknowledge()) {
                    logger.debug("Current message requires acknowledge.");
                    logger.debug("I'm inside the synchronized block, awaiting notify.");
                    this.synchronizer.wait();
                    break;
                }
                logger.debug("Message doesn't require notify or notify received, moving on.");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Error while acknowlediging message, exception was " + e.getMessage(), e);
            }
        }
    }

    private Optional<AbstractMessage> playTape() {
        if (this.messageRecorder.hasMoreMessages()) {
            return this.messageRecorder.playNextMessage();
        }
        if (this.endOfTape) {
            return Optional.empty();
        }
        this.endOfTape = true;
        return Optional.of(new EndOfTapeMessage(IncomingMessageType.ENDOFTAPE.toString()));
    }

    /**
     * Checks if this {@link RuntimeReplayListener} is done.
     * 
     * @return True if no more messages to handle, otherwise false.
     */
    public boolean isDone() {
        return done;
    }
}