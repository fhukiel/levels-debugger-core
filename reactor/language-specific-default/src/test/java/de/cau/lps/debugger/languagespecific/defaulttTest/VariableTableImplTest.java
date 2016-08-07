package de.cau.lps.debugger.languagespecific.defaulttTest;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.languagespecific.api.VariableTable;
import de.cau.lps.debugger.languagespecific.defaultt.VariableTableImpl;

/**
 * Tests the {@link VariableTableImpl} class.
 * 
 * @author Thomas Ulrich
 */
public class VariableTableImplTest {
    /**
     * Initializes required objects.
     */
    @Before
    public void initialize() {
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        VariableTable table = new VariableTableImpl();
        Assert.assertNotNull(table);
    }

    /**
     * Tests the update method.
     */
    @Test
    public void update() {
        String variableName = "varA";
        String variableValue = "42";
        String updatedValue = "43";

        ScopeIdentifier scope = new ScopeIdentifier("firstScope");
        VariableTable table = new VariableTableImpl();
        Variable originalVar = new Variable(variableName, variableValue, "0x00", new HashMap<String, String>());
        Variable updatedVar = new Variable(variableName, updatedValue, "0x00", new HashMap<String, String>());

        table.update(scope, originalVar);
        Assert.assertEquals(originalVar, table.getVisibleInScope(scope).get(0));

        table.update(scope, updatedVar);
        Assert.assertEquals(updatedVar, table.getVisibleInScope(scope).get(0));
    }

    /**
     * Tests the getVisibleInScope method.
     */
    @Test
    public void getVisibleInScope() {
        String variableAName = "varA";
        String variableAValue = "42";
        String variableBName = "varB";
        String variableBValue = "43";
        ScopeIdentifier firstScope = new ScopeIdentifier("firstScope");
        ScopeIdentifier secondScope = new ScopeIdentifier("secondScope");
        VariableTable table = new VariableTableImpl();
        Variable varA = new Variable(variableAName, variableAValue, "0x00", new HashMap<String, String>());
        Variable varB = new Variable(variableBName, variableBValue, "0x00", new HashMap<String, String>());

        table.update(firstScope, varA);
        table.update(secondScope, varB);
        Assert.assertEquals(varA, table.getVisibleInScope(firstScope).get(0));
        Assert.assertEquals(varB, table.getVisibleInScope(secondScope).get(0));
    }

    /**
     * Tests the getAll method.
     */
    @Test
    public void getAll() {
        String variableAName = "varA";
        String variableAValue = "42";
        String variableBName = "varB";
        String variableBValue = "43";
        ScopeIdentifier firstScope = new ScopeIdentifier("firstScope");
        ScopeIdentifier secondScope = new ScopeIdentifier("secondScope");
        VariableTable table = new VariableTableImpl();
        Variable varA = new Variable(variableAName, variableAValue, "0x00", new HashMap<String, String>());
        Variable varB = new Variable(variableBName, variableBValue, "0x00", new HashMap<String, String>());

        table.update(firstScope, varA);
        table.update(secondScope, varB);
        Assert.assertEquals(2, table.getAll().size());
    }

    /**
     * Tests the dropScope method.
     */
    @Test
    public void dropScope() {
        String variableAName = "varA";
        String variableAValue = "42";
        String variableBName = "varB";
        String variableBValue = "43";
        ScopeIdentifier firstScope = new ScopeIdentifier("firstScope");
        ScopeIdentifier secondScope = new ScopeIdentifier("secondScope");
        VariableTable table = new VariableTableImpl();
        Variable varA = new Variable(variableAName, variableAValue, "0x00", new HashMap<String, String>());
        Variable varB = new Variable(variableBName, variableBValue, "0x00", new HashMap<String, String>());

        table.update(firstScope, varA);
        table.update(secondScope, varB);
        table.dropScope(secondScope);
        Assert.assertEquals(varA, table.getVisibleInScope(firstScope).get(0));
        Assert.assertTrue(table.getVisibleInScope(secondScope).isEmpty());
    }

    /**
     * Tests the clone method.
     */
    @Test
    public void testClone() {
        String variableAName = "varA";
        String variableAValue = "42";
        String variableBName = "varB";
        String variableBValue = "43";
        ScopeIdentifier firstScope = new ScopeIdentifier("firstScope");
        ScopeIdentifier secondScope = new ScopeIdentifier("secondScope");
        VariableTable table = new VariableTableImpl();
        Variable varA = new Variable(variableAName, variableAValue, "0x00", new HashMap<String, String>());
        Variable varB = new Variable(variableBName, variableBValue, "0x00", new HashMap<String, String>());

        table.update(firstScope, varA);
        table.update(secondScope, varB);
        VariableTable clone = table.clone();
        Assert.assertEquals(varA, clone.getVisibleInScope(firstScope).get(0));
        Assert.assertEquals(varB, clone.getVisibleInScope(secondScope).get(0));
    }
}
