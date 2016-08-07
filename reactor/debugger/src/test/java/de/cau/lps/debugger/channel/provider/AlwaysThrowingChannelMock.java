package de.cau.lps.debugger.channel.provider;

import java.io.IOException;
import java.util.Optional;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.channel.CommunicationChannelTarget;
import de.cau.lps.debugger.messages.AbstractMessage;

/**
 * Mock for a {@link CommunicationChannel} that throws on every class that has a Throws clause.
 * 
 * @author Thomas Ulrich
 */
public class AlwaysThrowingChannelMock implements CommunicationChannel {

    private CommunicationChannelTarget target;

    /**
     * Creates a new instance of the {@link AlwaysThrowingChannelMock} class.
     * 
     * @param target
     *            A {@link CommunicationChannelTarget}.
     */
    public AlwaysThrowingChannelMock(CommunicationChannelTarget target) {
        this.target = target;
    }

    @Override
    public void close() throws IOException {
        throw new IOException();
    }

    @Override
    public void sendMessage(AbstractMessage message) throws IOException {
        throw new IOException();
    }

    @Override
    public Optional<String> receiveMessage() {
        return Optional.empty();
    }

    @Override
    public void open() throws Exception {
        throw new IOException();
    }

    @Override
    public CommunicationChannelTarget getTarget() {
        return this.target;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}
