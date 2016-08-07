package de.cau.lps.debugger.programtest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.channel.provider.CommunicationChannelMock;
import de.cau.lps.debugger.channel.provider.CommunicationChannelProvider;
import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.common.VariableTableFactory;
import de.cau.lps.debugger.commonMocks.VariableTableFactoryMock;
import de.cau.lps.debugger.commonMocks.VariableTableMock;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.messages.outgoing.runtime.NextStepRequestedMessage;
import de.cau.lps.debugger.program.Debugger;
import de.cau.lps.debugger.program.DebuggerImpl;

/**
 * Tests the {@link DebuggerImpl} class.
 * 
 * @author Thomas Ulrich
 */
public class DebuggerImplTest {

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
        Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
            CommunicationChannelProvider.getRuntimeChannel(true));
        Assert.assertNotNull(debugger);
    }

    /**
     * Pushes a call onto the call stack while the stepOverNextCall flag is not set.
     */
    @Test
    public void pushUnmarkedCall() {
        String methodName = "myMethod";
        String callId = "42";
        List<String> arguments = Arrays.asList("arg1");
        Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
            CommunicationChannelProvider.getRuntimeChannel(true));
        debugger.getState().setVariableTable(new VariableTableMock());
        Call call = new Call(methodName, arguments, callId, new HashMap<String, String>());
        debugger.pushOntoCallStack(call);
        Assert.assertTrue(debugger.getState().getCallStack().contains(call));
        Assert.assertFalse(debugger.getState().isStepOverNextCall());
    }

    /**
     * Pushes a call onto the call stack while the stepOverNextCall flag is set.
     */
    @Test
    public void pushMarkedCall() {
        String methodName = "myMethod";
        String callId = "42";
        List<String> arguments = Arrays.asList("arg1");
        Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
            CommunicationChannelProvider.getRuntimeChannel(true));
        debugger.getState().setVariableTable(new VariableTableMock());
        debugger.getState().setStepOverNextCall(true);
        Call call = new Call(methodName, arguments, callId, new HashMap<String, String>());
        debugger.pushOntoCallStack(call);
        Assert.assertTrue(debugger.getState().getCallStack().contains(call));
        Assert.assertTrue(call.isMarked());
        Assert.assertFalse(debugger.getState().isStepOverNextCall());
    }

    /**
     * Pops an unmarked call from the call stack.
     */
    @Test
    public void popCall() {
        String methodName = "myMethod";
        String callId = "42";
        List<String> arguments = Arrays.asList("arg1");
        Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
            CommunicationChannelProvider.getRuntimeChannel(true));
        debugger.getState().setVariableTable(new VariableTableMock());
        Call call = new Call(methodName, arguments, callId, new HashMap<String, String>());
        debugger.pushOntoCallStack(call);
        Call popped = debugger.popFromCallStack();
        Assert.assertEquals(call.getMethodName(), popped.getMethodName());
        Assert.assertEquals(call.getAssociatedPushMessageID(), popped.getAssociatedPushMessageID());
    }

    /**
     * Pops a marked call from the call stack.
     */
    @Test
    public void popMarkedCall() {
        String methodName = "myMethod";
        String callId = "42";
        List<String> arguments = Arrays.asList("arg1");
        Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
            CommunicationChannelProvider.getRuntimeChannel(true));
        debugger.getState().setVariableTable(new VariableTableMock());
        debugger.getState().setStepOverNextCall(true);
        Call call = new Call(methodName, arguments, callId, new HashMap<String, String>());
        debugger.pushOntoCallStack(call);
        Call popped = debugger.popFromCallStack();
        Assert.assertEquals(call.getMethodName(), popped.getMethodName());
        Assert.assertEquals(call.getAssociatedPushMessageID(), popped.getAssociatedPushMessageID());
    }

    /**
     * Updates the variable table.
     */
    @Test
    public void updateTable() {
        String variableName = "varA";
        String variableValue = "42";
        String variableAddress = "0x00001";
        Variable var = new Variable(variableName, variableValue, variableAddress, new HashMap<String, String>());
        Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
            CommunicationChannelProvider.getRuntimeChannel(true));
        debugger.getState().setVariableTable(new VariableTableMock());
        debugger.updateTable(var);
        List<Variable> all = debugger.getState().getVariableTable().getAll();
        Assert.assertEquals(variableValue, all.get(0).getValue());
    }

    /**
     * Tests the startReplay method.
     */
    @Test
    public void startReplay() {
        try {
            String methodName = "myMethod";
            String callId = "42";
            List<String> arguments = Arrays.asList("arg1");

            Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
                CommunicationChannelProvider.getRuntimeChannel(true));
            debugger.getState().setVariableTable(new VariableTableMock());
            debugger.getState().setStepOverNextCall(true);
            Call call = new Call(methodName, arguments, callId, new HashMap<String, String>());
            debugger.pushOntoCallStack(call);
            Assert.assertTrue(debugger.getState().getCallStack().contains(call));
            Assert.assertTrue(call.isMarked());
            Assert.assertFalse(debugger.getState().isStepOverNextCall());

            debugger.startReplay(callId);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the stopReplay method.
     */
    @Test
    public void stopReplay() {
        try {
            String methodName = "myMethod";
            String callId = "42";
            List<String> arguments = Arrays.asList("arg1");

            Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
                CommunicationChannelProvider.getRuntimeChannel(true));
            debugger.getState().setVariableTable(new VariableTableMock());
            debugger.getState().setStepOverNextCall(true);
            Call call = new Call(methodName, arguments, callId, new HashMap<String, String>());
            debugger.pushOntoCallStack(call);
            Assert.assertTrue(debugger.getState().getCallStack().contains(call));
            Assert.assertTrue(call.isMarked());
            Assert.assertFalse(debugger.getState().isStepOverNextCall());

            debugger.startReplay(callId);
            debugger.stopReplay();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the setPosition method.
     */
    @Test
    public void setPosition() {
        Position pos = new Position(1, 2);
        Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
            CommunicationChannelProvider.getRuntimeChannel(true));
        debugger.setPosition(pos);

        Assert.assertEquals(pos, debugger.getState().getPosition());
    }

    /**
     * Tests the sendStepSignalToRuntime method.
     */
    @Test
    public void sendStepSignalToRuntime() {
        try (CommunicationChannelMock runtimeChannel = (CommunicationChannelMock) CommunicationChannelProvider
            .getRuntimeChannel(true)) {
            Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true), runtimeChannel);
            debugger.sendStepSignalToRuntime();
            Assert.assertTrue(runtimeChannel.getSentMessages().get(0) instanceof NextStepRequestedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the run method.
     */
    @Test
    public void run() {
        try {
            Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
                CommunicationChannelProvider.getRuntimeChannel(true));
            Thread thread = new Thread((Runnable) debugger);
            thread.start();
            thread.join();
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the isBreakpoint method.
     */
    @Test
    public void isBreakpoint() {
        Position breakPoint = new Position(1, 2);
        Position other = new Position(2, 1);

        Debugger debugger = new DebuggerImpl(CommunicationChannelProvider.getViewChannel(true),
            CommunicationChannelProvider.getRuntimeChannel(true));
        debugger.getBreakPoints().add(breakPoint);
        Assert.assertTrue(debugger.isBreakpoint(breakPoint));
        Assert.assertFalse(debugger.isBreakpoint(other));
    }
}
