package de.cau.lps.debugger.languagespecific.apitest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.languagespecific.api.Variable;

/**
 * Tests the {@link Variable} class.
 * 
 * @author Thomas Ulrich
 */
public class VariableTest {

    private static String variableName;
    private static String variableValue;
    private static String variableAddress;
    private static Map<String, String> options;
    private static String optionAKey;
    private static String optionAValue;
    private static String optionBKey;
    private static String optionBValue;

    /**
     * Initializes required objects.
     */
    @Before
    public void initialize() {
        variableName = "varName";
        variableValue = "alpha";
        variableAddress = "0x0001";
        options = new HashMap<String, String>();
        options.put(optionAKey, optionAValue);
        options.put(optionBKey, optionBValue);
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        Variable variable = new Variable(variableName, variableValue, variableAddress, options);
        Assert.assertEquals(variableName, variable.getName());
        Assert.assertEquals(variableValue, variable.getValue());
        Assert.assertEquals(variableAddress, variable.getAddress());
        Assert.assertEquals(optionAValue, variable.getOptions().get(optionAKey));
        Assert.assertEquals(optionBValue, variable.getOptions().get(optionBKey));
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor3() {
        Variable other = new Variable(variableName, variableValue, variableAddress, options);
        Variable variable = new Variable(other);
        Assert.assertEquals(variableName, variable.getName());
        Assert.assertEquals(variableValue, variable.getValue());
        Assert.assertEquals(variableAddress, variable.getAddress());
        Assert.assertEquals(optionAValue, variable.getOptions().get(optionAKey));
        Assert.assertEquals(optionBValue, variable.getOptions().get(optionBKey));
    }
}
