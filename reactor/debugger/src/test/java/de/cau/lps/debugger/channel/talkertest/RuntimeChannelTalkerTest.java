package de.cau.lps.debugger.channel.talkertest;

import org.junit.Assert;
import org.junit.Test;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.channel.CommunicationChannelTarget;
import de.cau.lps.debugger.channel.provider.AlwaysThrowingChannelMock;
import de.cau.lps.debugger.channel.provider.CommunicationChannelMock;
import de.cau.lps.debugger.channel.provider.CommunicationChannelProvider;
import de.cau.lps.debugger.channel.talker.RuntimeChannelTalker;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.runtime.NextStepRequestedMessage;

/**
 * Tests the {@link RuntimeChannelTalker} class.
 * 
 * @author Thomas Ulrich
 */
public class RuntimeChannelTalkerTest {

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        Assert.assertNotNull(new RuntimeChannelTalker(CommunicationChannelProvider.getRuntimeChannel(true)));
    }

    /**
     * Tests the sendStepSignal method.
     */
    @Test
    public void sendStepSignal() {
        try (CommunicationChannelMock mockChannel = (CommunicationChannelMock) CommunicationChannelProvider
            .getRuntimeChannel(true)) {
            RuntimeChannelTalker talker = new RuntimeChannelTalker(mockChannel);
            talker.sendStepSignal();
            NextStepRequestedMessage sent = (NextStepRequestedMessage) mockChannel.getSentMessages().get(0);
            Assert.assertEquals(sent.toString(),
                new NextStepRequestedMessage(OutgoingMessageType.NEXTSTEPREQUESTED.toString()).toString());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the recovery mechanism when a message cannot be sent.
     */
    @Test
    public void failToSend() {
        boolean hasThrown = false;
        try (CommunicationChannel mockChannel = new AlwaysThrowingChannelMock(CommunicationChannelTarget.RUNTIME)) {
            RuntimeChannelTalker talker = new RuntimeChannelTalker(mockChannel);
            talker.sendStepSignal();
        } catch (Exception e) {
            hasThrown = true;
            Assert.assertTrue(true);
        }
        Assert.assertTrue(hasThrown);
    }
}
