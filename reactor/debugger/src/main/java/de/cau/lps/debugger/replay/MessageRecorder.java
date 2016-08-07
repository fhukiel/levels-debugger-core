package de.cau.lps.debugger.replay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.runtime.PushOntoCallStackMessage;

/**
 * This class wraps a list of {@link AbstractMessage}s recorded from the runtime.
 * 
 * @author Thomas Ulrich
 */
public class MessageRecorder {

    private static Logger logger = LoggerFactory.getLogger(MessageRecorder.class);
    private List<AbstractMessage> messages;
    private int nextMessageIndex;
    private Map<String, Integer> pushMessageIndices;
    private boolean recordingEnabled;

    /**
     * Initializes a new instance of the {@link MessageRecorder} class.
     */
    public MessageRecorder() {
        this.nextMessageIndex = 0;
        this.messages = new ArrayList<AbstractMessage>();
        this.pushMessageIndices = new HashMap<String, Integer>();
        this.recordingEnabled = true;
    }

    /**
     * Initializes a new instance of the {@link MessageRecorder} class.
     * 
     * @param other
     *            Another {@link MessageRecorder} to clone.
     */
    public MessageRecorder(MessageRecorder other) {
        this();
        this.messages = new ArrayList<AbstractMessage>(other.messages);
        this.pushMessageIndices = new HashMap<String, Integer>(other.pushMessageIndices);
        this.recordingEnabled = other.recordingEnabled;
        /*
         * References are simply copied instead of creating new MessageRecorders. However, a new MessageRecorder
         * instance is created when accessing the predecessor / successor via the getters.
         * 
         * Otherwise an endless loop / stack overflow ensues whenever a new instance is created since cloning the
         * predecessor of a machine via this constructor would also mean cloning this MessageRecorder which would mean
         * cloning the predecessor once more and so on.
         * 
         */
    }

    /**
     * Takes an {@link AbstractMessage} and adds it to the list of messages that can be replayed using this object.
     * 
     * @param message
     *            The AbstractMessage to add to the replay list.
     */
    public void record(AbstractMessage message) {
        if (this.recordingEnabled) {
            logger.debug("MessageRecorder records message.");
            this.messages.add(message);
            if (message instanceof PushOntoCallStackMessage) {
                logger.debug("Adding mapping from call id '" + message.getId() + "' to index " + this.messages.size());
                this.pushMessageIndices.put(message.getId(), this.messages.size());
            }
        }
    }

    /**
     * Forwards the tape to the push message of the requested call.
     * 
     * @param callId
     *            The call to which to forward the tape.
     */
    public void forwardToCall(String callId) {
        logger.debug("Attempting to forward to call '" + callId + "'.");
        Integer nextIndex = this.pushMessageIndices.get(callId);
        if (nextIndex != null) {
            this.nextMessageIndex = nextIndex;
            logger.debug("Successfully forwarded to call '" + callId + "'.");
        } else {
            logger.error("Could not forward to call '" + callId + "', id unknown.");
        }
    }

    /**
     * Replays the next {@link AbstractMessage} from the list of recorded messages.
     * 
     * @return An Optional {@link AbstractMessage}. Optional will be empty if end of list is reached.
     */
    public Optional<AbstractMessage> playNextMessage() {
        if (this.hasMoreMessages()) {
            logger.debug("MessageRecorder plays message " + nextMessageIndex);
            return Optional.of(this.messages.get(nextMessageIndex++));
        }
        return Optional.empty();
    }

    /**
     * Checks if more messages are available.
     * 
     * @return True if more messages are available, otherwise false.
     */
    public boolean hasMoreMessages() {
        boolean hasMore = this.messages.size() > nextMessageIndex;
        logger.debug("MessageRecorder has more messages: " + hasMore);
        return hasMore;
    }

    /**
     * Gets the recordingEnabled flag.
     * 
     * @return True if recording is enabled, otherwise false.
     */
    public boolean isRecordingEnabled() {
        return recordingEnabled;
    }

    /**
     * Sets the recordingEnabled flag.
     * 
     * @param recordingEnabled
     *            True if recording is enabled, otherwise false.
     */
    public void setRecordingEnabled(boolean recordingEnabled) {
        this.recordingEnabled = recordingEnabled;
    }
}