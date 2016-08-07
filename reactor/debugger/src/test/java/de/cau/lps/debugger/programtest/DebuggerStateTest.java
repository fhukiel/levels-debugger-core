package de.cau.lps.debugger.programtest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.commonMocks.VariableTableMock;
import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.program.DebuggerState;

/**
 * Tests the {@link DebuggerState} class.
 * 
 * @author Thomas Ulrich
 */
public class DebuggerStateTest {

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        Assert.assertNotNull(new DebuggerState());
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor2() {
        final String methodName = "myMethod";
        final String callId = "42";
        final List<String> arguments = Arrays.asList("arg1");
        final String variableName = "varA";
        final String variableValue = "varAValue";
        final String variableAddress = "0x0001";
        final ScopeIdentifier variableScope = new ScopeIdentifier("aScope");
        final Position currentPosition = new Position(2, 1);
        final boolean autoStep = true;
        final boolean stepOverNextCall = true;
        final Variable var = new Variable(variableName, variableValue, variableAddress, new HashMap<String, String>());

        DebuggerState original = new DebuggerState();
        original.setVariableTable(new VariableTableMock());
        original.getCallStack().push(new Call(methodName, arguments, callId, new HashMap<String, String>()));
        original.getVariableTable().update(variableScope, var);
        original.setPosition(currentPosition);
        original.setAutoStep(autoStep);
        original.setStepOverNextCall(stepOverNextCall);

        DebuggerState copy = new DebuggerState(original);
        List<Variable> all = copy.getVariableTable().getAll();

        Assert.assertEquals(original.getCallStack().size(), copy.getCallStack().size());
        Assert.assertEquals(variableValue, all.get(0).getValue());
        Assert.assertEquals(currentPosition, copy.getPosition());
        Assert.assertEquals(autoStep, copy.isAutoStep());
        Assert.assertEquals(stepOverNextCall, copy.isStepOverNextCall());
    }

}
