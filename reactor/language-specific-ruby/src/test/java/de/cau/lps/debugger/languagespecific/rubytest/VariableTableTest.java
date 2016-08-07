package de.cau.lps.debugger.languagespecific.rubytest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.languagespecific.api.VariableTable;
import de.cau.lps.debugger.languagespecific.ruby.ScopeType;
import de.cau.lps.debugger.languagespecific.ruby.VariableTableImpl;

/**
 * Tests the {@link VariableTable} class.
 * 
 * @author Thomas Ulrich
 */
public class VariableTableTest {

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
     * Tests the constructor.
     */
    @Test
    public void ctor2() {
        String scope = "111";
        ScopeIdentifier scopeId = new ScopeIdentifier(scope);
        String address = "varAddress";
        String name = "varName";
        ScopeType scopeType = ScopeType.LOCAL;
        String classId = "MyClass";
        String instanceId = "MyInstance";
        Map<String, String> options = new HashMap<String, String>();
        options.put(VariableTableImpl.SCOPE_TYPE_KEY, scopeType.toString());
        options.put(VariableTableImpl.DEFINING_CLASS_ID_KEY, classId);
        options.put(VariableTableImpl.DEFINING_INSTANCE_ID_KEY, instanceId);

        Variable var = new Variable(name, "varValue", address, options);
        VariableTable other = new VariableTableImpl();
        other.update(scopeId, var);

        VariableTable table = other.clone();
        Assert.assertNotNull(table);
        System.out.println("total defined: " + table.getAll().size());

        List<Variable> defined = table.getVisibleInScope(scopeId);
        Assert.assertEquals(var, defined.get(0));
    }

    /**
     * Tests the update mechanism with a variable that's not already defined.
     */
    @Test
    public void updateUndefinedScope() {
        String scope = "111";
        ScopeIdentifier scopeId = new ScopeIdentifier(scope);
        String address = "varAddress";
        String name = "varName";
        ScopeType scopeType = ScopeType.LOCAL;
        String classId = "MyClass";
        String instanceId = "MyInstance";
        Map<String, String> options = new HashMap<String, String>();
        options.put(VariableTableImpl.SCOPE_TYPE_KEY, scopeType.toString());
        options.put(VariableTableImpl.DEFINING_CLASS_ID_KEY, classId);
        options.put(VariableTableImpl.DEFINING_INSTANCE_ID_KEY, instanceId);

        Variable var = new Variable(name, "varValue", address, options);
        VariableTable table = new VariableTableImpl();
        table.update(scopeId, var);
        List<Variable> defined = table.getVisibleInScope(scopeId);
        Assert.assertEquals(var, defined.get(0));
    }

    /**
     * Tests the update mechanism with a variable that's already defined.
     */
    @Test
    public void updateDefinedScope() {
        String scope = "111";
        ScopeIdentifier scopeId = new ScopeIdentifier(scope);
        String address = "varAddress";
        String name = "varName";
        String value = "oldValue";
        String updatedValue = "updatedValue";
        ScopeType scopeType = ScopeType.LOCAL;
        String classId = "MyClass";
        String instanceId = "MyInstance";
        Map<String, String> options = new HashMap<String, String>();
        options.put(VariableTableImpl.SCOPE_TYPE_KEY, scopeType.toString());
        options.put(VariableTableImpl.DEFINING_CLASS_ID_KEY, classId);
        options.put(VariableTableImpl.DEFINING_INSTANCE_ID_KEY, instanceId);

        Variable var = new Variable(name, value, address, options);
        VariableTable table = new VariableTableImpl();
        table.update(scopeId, var);
        List<Variable> defined = table.getVisibleInScope(scopeId);
        Assert.assertEquals(var, defined.get(0));

        Variable updatedVar = new Variable(var);
        updatedVar.setValue(updatedValue);
        table.update(scopeId, updatedVar);
        defined = table.getVisibleInScope(scopeId);
        Assert.assertEquals(updatedVar, defined.get(0));
    }
}
