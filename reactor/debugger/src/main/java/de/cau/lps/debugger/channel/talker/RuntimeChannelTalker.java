package de.cau.lps.debugger.channel.talker;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.runtime.NextStepRequestedMessage;

/**
 * Sends messages to a runtime {@link CommunicationChannel}.
 * 
 * @author Thomas Ulrich
 */
public class RuntimeChannelTalker extends AbstractChannelTalker {
    private static Logger logger = LoggerFactory.getLogger(RuntimeChannelTalker.class);

    /**
     * Initializes a new instance of the {@link RuntimeChannelTalker} class.
     * 
     * @param channel
     *            A {@link CommunicationChannel} object to send messages to.
     */
    public RuntimeChannelTalker(CommunicationChannel channel) {
        super(channel);
    }

    /**
     * Sends a STEP signal to the runtime.
     * 
     * @throws IOException
     *             Thrown if an exception occurs while sending.
     */
    public void sendStepSignal() throws IOException {
        logger.debug("Sending step signal to runtime.");
        this.sendMessage(new NextStepRequestedMessage(OutgoingMessageType.NEXTSTEPREQUESTED.toString()));
    }
}
