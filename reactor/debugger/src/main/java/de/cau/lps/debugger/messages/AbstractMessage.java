package de.cau.lps.debugger.messages;

import java.util.UUID;

/**
 * The abstract base class for all messages.
 * 
 * @author Thomas Ulrich
 *
 */
public abstract class AbstractMessage {

    private String id;

    protected boolean requiresAcknowledge;

    /**
     * Initializes a new instance of the {@link AbstractMessage} class.
     */
    public AbstractMessage() {
        this.id = UUID.randomUUID().toString();
        this.requiresAcknowledge = false;
    }

    /**
     * Gets a string representation of this class.
     * 
     * @return A string encapsulating the payload of this message.
     */
    protected abstract String getStringRepresentation();

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getStringRepresentation();
    }

    /**
     * Gets this message's unique Id.
     * 
     * @return The unique Id.
     */
    public String getId() {
        return id;
    }

    /**
     * Signals whether this message must be acknowledged with a reply or not.
     * 
     * @return True if this message must be acknowledged, otherwise false.
     */
    public boolean isRequiresAcknowledge() {
        return requiresAcknowledge;
    }
}