package de.cau.lps.debugger.channel.provider;

import java.util.ArrayList;
import java.util.List;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.channel.CommunicationChannelTarget;
import de.cau.lps.debugger.messages.AbstractMessage;

/**
 * Creates a {@link CommunicationChannel} for testing purposes.
 */
public class CommunicationChannelProvider {

    /**
     * Gets a new empty view {@link CommunicationChannel}.
     * 
     * @param autoPlay
     *            Indicates whether to replay messages automatically or whether to replay them one by one when notifyall
     *            is called on the channel.
     * @return A new {@link CommunicationChannel} with {@link CommunicationChannelTarget} view.
     */
    public static CommunicationChannel getViewChannel(boolean autoPlay) {
        return new CommunicationChannelMock(CommunicationChannelTarget.VIEW, new ArrayList<AbstractMessage>(),
            autoPlay);
    }

    /**
     * Gets a new view {@link CommunicationChannel} that replays a tape of messages.
     * 
     * @param tape
     *            The tape of {@link AbstractMessage}s to replay.
     * @param autoPlay
     *            Indicates whether to replay messages automatically or whether to replay one by one when notifyall is
     *            called on the channel.
     * @return A new {@link CommunicationChannel} with {@link CommunicationChannelTarget} view.
     */
    public static CommunicationChannel getViewChannel(List<AbstractMessage> tape, boolean autoPlay) {
        return new CommunicationChannelMock(CommunicationChannelTarget.VIEW, tape, autoPlay);
    }

    /**
     * Gets a new empty runtime {@link CommunicationChannel}.
     * 
     * @param autoPlay
     *            Indicates whether to replay messages automatically or whether to replay them one by one when notifyall
     *            is called on the channel.
     * @return A new empty {@link CommunicationChannel} with {@link CommunicationChannelTarget} runtime.
     */
    public static CommunicationChannel getRuntimeChannel(boolean autoPlay) {
        return new CommunicationChannelMock(CommunicationChannelTarget.RUNTIME, new ArrayList<AbstractMessage>(),
            autoPlay);
    }

    /**
     * Gets a new runtime {@link CommunicationChannel} that replays a tape of messages.
     * 
     * @param tape
     *            The tape of {@link AbstractMessage}s to replay.
     * @param autoPlay
     *            Indicates whether to replay messages automatically or whether to replay them one by one when notifyall
     *            is called on the channel.
     * @return A new {@link CommunicationChannel} with {@link CommunicationChannelTarget} runtime.
     */
    public static CommunicationChannel getRuntimeChannel(List<AbstractMessage> tape, boolean autoPlay) {
        return new CommunicationChannelMock(CommunicationChannelTarget.RUNTIME, tape, autoPlay);
    }
}
