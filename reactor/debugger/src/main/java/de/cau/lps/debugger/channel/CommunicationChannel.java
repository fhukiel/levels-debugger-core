package de.cau.lps.debugger.channel;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

import de.cau.lps.debugger.messages.AbstractMessage;

/**
 * A two-way means of communication.
 * 
 * @author Thomas Ulrich
 */
public interface CommunicationChannel extends Closeable {

    /**
     * Sends a message to this channel.
     * 
     * @param message
     *            An {@link AbstractMessage} object to send.
     * @throws IOException
     *             Thrown if an error occurs during transmission.
     */
    public void sendMessage(AbstractMessage message) throws IOException;

    /**
     * Receives a message from this channel. Blocks if channel is empty.
     * 
     * @return An Optional of type String, depending on whether something could be read from Channel.
     */
    public Optional<String> receiveMessage();

    /**
     * Opens the channel for communication.
     * 
     * @throws Exception
     *             Thrown if channel cannot be opened.
     */
    public void open() throws Exception;

    /**
     * Gets the {@link CommunicationChannelTarget} of this channel.
     * 
     * @return A {@link CommunicationChannelTarget} value.
     */
    public CommunicationChannelTarget getTarget();

    /**
     * Signals whether this channel is open and ready for communication or not.
     * 
     * @return True if channel can be used for communication, otherwise false.
     */
    public boolean isAvailable();
}
