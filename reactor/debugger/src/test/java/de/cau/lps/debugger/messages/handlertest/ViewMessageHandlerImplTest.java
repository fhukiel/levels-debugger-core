package de.cau.lps.debugger.messages.handlertest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.common.VariableTableFactory;
import de.cau.lps.debugger.commonMocks.DebuggerMock;
import de.cau.lps.debugger.commonMocks.VariableTableFactoryMock;
import de.cau.lps.debugger.exception.UnknownMessageTypeException;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.handler.ViewMessageHandler;
import de.cau.lps.debugger.messages.handler.ViewMessageHandlerImpl;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.AddBreakpointMessage;
import de.cau.lps.debugger.messages.incoming.view.RemoveBreakpointMessage;
import de.cau.lps.debugger.messages.incoming.view.RunToNextBreakpointMessage;
import de.cau.lps.debugger.messages.incoming.view.StartReplayMessage;
import de.cau.lps.debugger.messages.incoming.view.StepMessage;
import de.cau.lps.debugger.messages.incoming.view.StepOverMessage;
import de.cau.lps.debugger.messages.incoming.view.StopReplayMessage;
import de.cau.lps.debugger.messages.outgoing.runtime.NextStepRequestedMessage;

/**
 * Tests the {@link ViewMessageHandlerImpl} class.
 * 
 * @author Thomas Ulrich
 */
public class ViewMessageHandlerImplTest {

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        VariableTableFactory.setConcreteFactory(new VariableTableFactoryMock());
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        Assert.assertNotNull(new ViewMessageHandlerImpl(new DebuggerMock()));
    }

    /**
     * Tests the handling of a {@link StepMessage}.
     */
    @Test
    public void stepMessage() {
        try {
            DebuggerMock debugger = new DebuggerMock();
            ViewMessageHandler handler = new ViewMessageHandlerImpl(debugger);
            StepMessage msg = new StepMessage(IncomingMessageType.STEP.toString());
            handler.handleIncomingViewMessage(msg);
            Assert.assertTrue(debugger.isStepMessageSent());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the handling of a {@link StepOverMessage}.
     */
    @Test
    public void stepOverMessage() {
        try {
            DebuggerMock debugger = new DebuggerMock();
            ViewMessageHandler handler = new ViewMessageHandlerImpl(debugger);
            StepOverMessage msg = new StepOverMessage(IncomingMessageType.STEPOVER.toString());
            handler.handleIncomingViewMessage(msg);
            Assert.assertTrue(debugger.getState().isStepOverNextCall());
            Assert.assertTrue(debugger.isStepMessageSent());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the handling of an {@link AddBreakpointMessage}.
     */
    @Test
    public void addBreakpoint() {
        try {
            int line = 1;
            int row = 2;
            Position breakPoint = new Position(line, row);

            DebuggerMock debugger = new DebuggerMock();
            ViewMessageHandler handler = new ViewMessageHandlerImpl(debugger);
            AddBreakpointMessage msg = new AddBreakpointMessage(IncomingMessageType.ADDBREAKPOINT
                + MessageFormatter.DELIMITER + line + MessageFormatter.DELIMITER + row);
            handler.handleIncomingViewMessage(msg);

            Assert.assertTrue(debugger.getBreakPoints().contains(breakPoint));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the handling of a {@link RemoveBreakpointMessage}.
     */
    @Test
    public void removeBreakpoint() {
        try {
            int line = 1;
            int row = 2;
            Position breakPoint = new Position(line, row);

            DebuggerMock debugger = new DebuggerMock();
            debugger.getBreakPoints().add(breakPoint);

            ViewMessageHandler handler = new ViewMessageHandlerImpl(debugger);
            RemoveBreakpointMessage msg = new RemoveBreakpointMessage(IncomingMessageType.REMOVEBREAKPOINT
                + MessageFormatter.DELIMITER + line + MessageFormatter.DELIMITER + row);
            handler.handleIncomingViewMessage(msg);
            Assert.assertFalse(debugger.getBreakPoints().contains(breakPoint));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the handling of a {@link RunToNextBreakpointMessage}.
     */
    @Test
    public void runToNextBreakpoint() {
        try {
            DebuggerMock debugger = new DebuggerMock();
            ViewMessageHandler handler = new ViewMessageHandlerImpl(debugger);
            RunToNextBreakpointMessage msg = new RunToNextBreakpointMessage(
                IncomingMessageType.RUNTONEXTBREAKPOINT.toString());
            handler.handleIncomingViewMessage(msg);
            Assert.assertTrue(debugger.getState().isAutoStep());
            Assert.assertTrue(debugger.isStepMessageSent());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the handling of a {@link StartReplayMessage}.
     */
    @Test
    public void startReplay() {
        try {
            String callId = "42";
            DebuggerMock debugger = new DebuggerMock();
            ViewMessageHandler handler = new ViewMessageHandlerImpl(debugger);
            StartReplayMessage msg = new StartReplayMessage(
                IncomingMessageType.STARTREPLAY.toString() + MessageFormatter.DELIMITER + callId);
            handler.handleIncomingViewMessage(msg);
            Assert.assertTrue(debugger.isReplay());
            Assert.assertEquals(callId, debugger.getReplayFrom());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the handling of a {@link StopReplayMessage}.
     */
    @Test
    public void stopReplay() {
        try {
            DebuggerMock debugger = new DebuggerMock();
            ViewMessageHandler handler = new ViewMessageHandlerImpl(debugger);
            StopReplayMessage msg = new StopReplayMessage(IncomingMessageType.STOPREPLAY.toString());
            handler.handleIncomingViewMessage(msg);
            Assert.assertFalse(debugger.isReplay());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the handling of an unknown message.
     */
    @Test
    public void unknownMessage() {
        try {
            DebuggerMock debugger = new DebuggerMock();
            ViewMessageHandler handler = new ViewMessageHandlerImpl(debugger);
            NextStepRequestedMessage msg = new NextStepRequestedMessage("do it");
            handler.handleIncomingViewMessage(msg);
            Assert.fail();
        } catch (UnknownMessageTypeException e) {
            return;
        }
        Assert.fail();
    }
}
