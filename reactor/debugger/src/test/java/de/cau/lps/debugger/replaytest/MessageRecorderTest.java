package de.cau.lps.debugger.replaytest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.RunToNextBreakpointMessage;
import de.cau.lps.debugger.messages.incoming.view.StepMessage;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.view.EndOfReplayTapeMessage;
import de.cau.lps.debugger.replay.MessageRecorder;

/**
 * Tests the {@link MessageRecorder} class.
 * 
 * @author Thomas Ulrich
 */
public class MessageRecorderTest {

    private static MessageRecorder other;
    private static AbstractMessage recordedMessage;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        recordedMessage = new EndOfReplayTapeMessage(OutgoingMessageType.ENDOFREPLAYTAPE.toString());
        other = new MessageRecorder();
        other.record(recordedMessage);
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        Assert.assertNotNull(new MessageRecorder());
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor2() {
        MessageRecorder recorder = new MessageRecorder(other);
        Assert.assertEquals(recordedMessage, recorder.playNextMessage().get());
    }

    /**
     * Tests the record method.
     */
    @Test
    public void record() {
        StepMessage msg = new StepMessage(IncomingMessageType.STEP.toString());
        MessageRecorder recorder = new MessageRecorder();
        Assert.assertFalse(recorder.hasMoreMessages());
        recorder.record(msg);
        Assert.assertTrue(recorder.hasMoreMessages());
    }

    /**
     * Tests the replay feature.
     */
    @Test
    public void replay() {
        StepMessage msg = new StepMessage(IncomingMessageType.STEP.toString());
        RunToNextBreakpointMessage msg2 = new RunToNextBreakpointMessage(
            IncomingMessageType.RUNTONEXTBREAKPOINT.toString());

        List<AbstractMessage> msgs = Arrays.asList(msg, msg2);
        MessageRecorder recorder = new MessageRecorder();

        for (AbstractMessage addMe : msgs) {
            recorder.record(addMe);
        }

        for (int i = 0; i < msgs.size(); i++) {
            Optional<AbstractMessage> maybeMessage = recorder.playNextMessage();
            Assert.assertTrue(maybeMessage.isPresent());
            AbstractMessage message = maybeMessage.get();
            Assert.assertEquals(msgs.get(i).toString(), message.toString());
        }
    }

    /**
     * Tests the replay feature on an empty machine.
     */
    @Test
    public void replayEmpty() {
        MessageRecorder recorder = new MessageRecorder();
        Optional<AbstractMessage> maybeMessage = recorder.playNextMessage();
        Assert.assertFalse(maybeMessage.isPresent());
    }
}
