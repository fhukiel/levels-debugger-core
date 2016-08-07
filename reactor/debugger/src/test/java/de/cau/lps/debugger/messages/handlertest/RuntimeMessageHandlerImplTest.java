package de.cau.lps.debugger.messages.handlertest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.common.VariableTableFactory;
import de.cau.lps.debugger.commonMocks.DebuggerMock;
import de.cau.lps.debugger.commonMocks.VariableTableFactoryMock;
import de.cau.lps.debugger.exception.UnknownMessageTypeException;
import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.handler.RuntimeMessageHandler;
import de.cau.lps.debugger.messages.handler.RuntimeMessageHandlerImpl;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.runtime.PopFromCallStackMessage;
import de.cau.lps.debugger.messages.incoming.runtime.PushOntoCallStackMessage;
import de.cau.lps.debugger.messages.incoming.runtime.UpdatePositionMessage;
import de.cau.lps.debugger.messages.incoming.runtime.UpdateTableMessage;
import de.cau.lps.debugger.messages.incoming.view.RunToNextBreakpointMessage;

/**
 * Tests the {@link RuntimeMessageHandler} class.
 * 
 * @author Thomas Ulrich
 */
public class RuntimeMessageHandlerImplTest {

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
        Assert.assertNotNull(new RuntimeMessageHandlerImpl(new DebuggerMock()));
    }

    /**
     * Tests the handling of a {@link PushOntoCallStackMessage}.
     */
    @Test
    public void pushOntoCallStack() {
        try {
            String methodName = "myMethod";
            PushOntoCallStackMessage msg = new PushOntoCallStackMessage(
                IncomingMessageType.PUSHONTOCALLSTACK + MessageFormatter.DELIMITER + methodName);

            DebuggerMock debugger = new DebuggerMock();
            RuntimeMessageHandler handler = new RuntimeMessageHandlerImpl(debugger);

            handler.handleIncomingRuntimeMessage(msg);
            Call call = debugger.getState().getCallStack().pop();

            Assert.assertEquals(methodName, call.getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the handling of a {@link PopFromCallStackMessage}.
     */
    @Test
    public void popFromCallStack() {
        try {
            DebuggerMock debugger = new DebuggerMock();
            debugger.getState().getCallStack()
                .push(new Call("blah", Arrays.asList("arg1"), "1", new HashMap<String, String>()));
            RuntimeMessageHandler handler = new RuntimeMessageHandlerImpl(debugger);

            PopFromCallStackMessage msg = new PopFromCallStackMessage(IncomingMessageType.POPFROMCALLSTACK.toString());

            handler.handleIncomingRuntimeMessage(msg);
            Assert.assertTrue(debugger.getState().getCallStack().isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the handling of an {@link UpdateTableMessage}.
     */
    @Test
    public void updateTable() {
        try {
            String variableName = "myVariable";
            String variableValue = "42";
            String variableAddress = "0x0111";
            DebuggerMock debugger = new DebuggerMock();
            ScopeIdentifier scopeId = new ScopeIdentifier(debugger.getState().getLocalScopeIdentifier());

            RuntimeMessageHandler handler = new RuntimeMessageHandlerImpl(debugger);

            UpdateTableMessage msg = new UpdateTableMessage(
                IncomingMessageType.UPDATETABLE + MessageFormatter.DELIMITER + variableName + MessageFormatter.DELIMITER
                    + variableValue + MessageFormatter.DELIMITER + variableAddress);

            handler.handleIncomingRuntimeMessage(msg);
            List<Variable> gotten = debugger.getState().getVariableTable().getVisibleInScope(scopeId);
            Assert.assertEquals(variableValue, gotten.get(0).getValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the handling of a {@link UpdatePositionMessage} message.
     */
    @Test
    public void updatePosition() {
        try {
            int line = 1;
            int row = 2;
            Position referencePosition = new Position(line, row);

            DebuggerMock debugger = new DebuggerMock();
            RuntimeMessageHandler handler = new RuntimeMessageHandlerImpl(debugger);

            UpdatePositionMessage msg = new UpdatePositionMessage(IncomingMessageType.UPDATEPOSITION
                + MessageFormatter.DELIMITER + line + MessageFormatter.DELIMITER + row);
            handler.handleIncomingRuntimeMessage(msg);
            Assert.assertEquals(referencePosition, debugger.getState().getPosition());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the behavior when an unknown message is passed.
     */
    @Test
    public void unknownMessage() {
        DebuggerMock debugger = new DebuggerMock();
        RuntimeMessageHandler handler = new RuntimeMessageHandlerImpl(debugger);

        RunToNextBreakpointMessage unknown = new RunToNextBreakpointMessage(
            IncomingMessageType.RUNTONEXTBREAKPOINT.toString());
        try {
            handler.handleIncomingRuntimeMessage(unknown);
            Assert.fail();
        } catch (UnknownMessageTypeException e) {
            Assert.assertTrue(true);
        }
    }
}