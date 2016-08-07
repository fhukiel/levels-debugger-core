package de.cau.lps.debugger.channel.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.channel.CommunicationChannelTarget;
import de.cau.lps.debugger.messages.AbstractMessage;

/**
 * Mock for a {@link CommunicationChannel}.
 * 
 * @author Thomas Ulrich
 */
public class CommunicationChannelMock implements CommunicationChannel {

    private CommunicationChannelTarget target;
    private List<AbstractMessage> tape;
    private int currentMessageIndex;
    private boolean autoPlay;

    private List<AbstractMessage> sentMessages;

    /**
     * Initializes a new instance of the {@link CommunicationChannelMock} class.
     * 
     * @param target
     *            A {@link CommunicationChannelTarget} value.
     * @param tape
     *            A List of {@link AbstractMessage}s to replay via this channel.
     * @param autoPlay
     *            A flag indicating that the channel should replay messages continously or if it requires a notify call
     *            on itself.
     */
    public CommunicationChannelMock(CommunicationChannelTarget target, List<AbstractMessage> tape, boolean autoPlay) {
        this.target = target;
        this.tape = tape;
        this.currentMessageIndex = 0;
        this.autoPlay = autoPlay;
        this.sentMessages = new ArrayList<AbstractMessage>();
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void sendMessage(AbstractMessage message) throws IOException {
        this.sentMessages.add(message);
    }

    @Override
    public Optional<String> receiveMessage() {
        if (this.isAvailable()) {
            AbstractMessage msg = this.tape.get(this.currentMessageIndex);
            this.currentMessageIndex++;
            while (!this.autoPlay) {
                try {
                    synchronized (this) {
                        this.wait();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return Optional.of(msg.toString());
        }
        return Optional.empty();
    }

    @Override
    public void open() throws Exception {
    }

    @Override
    public CommunicationChannelTarget getTarget() {
        return this.target;
    }

    @Override
    public boolean isAvailable() {
        return this.currentMessageIndex < this.tape.size();
    }

    /**
     * Gets the list of sent messages.
     * 
     * @return A list of {@link AbstractMessage}s.
     */
    public List<AbstractMessage> getSentMessages() {
        return sentMessages;
    }

}
