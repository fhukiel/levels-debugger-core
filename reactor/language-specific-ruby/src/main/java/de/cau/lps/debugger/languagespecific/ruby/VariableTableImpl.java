package de.cau.lps.debugger.languagespecific.ruby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.languagespecific.api.VariableTable;

/**
 * Manages the known variables and their placement in the correct scopes.
 * 
 * @author Thomas Ulrich
 */
public class VariableTableImpl extends VariableTable {
    private static Logger logger = LoggerFactory.getLogger(VariableTableImpl.class);

    /**
     * Key for the scope type option.
     */
    public static final String SCOPE_TYPE_KEY = "ScopeType";

    /**
     * Key for the defining class id option.
     */
    public static final String DEFINING_CLASS_ID_KEY = "DefiningClassId";

    /**
     * Key for the defining instance id option.
     */
    public static final String DEFINING_INSTANCE_ID_KEY = "DefiningInstanceId";

    /**
     * Key for the defining local scope id option.
     */
    public static final String LOCAL_SCOPE_ID = "LocalScope";

    Map<String, List<Variable>> all;
    Map<String, Variable> globalVariables;
    Map<String, Map<String, Variable>> classVariables;
    Map<String, Map<String, Variable>> instanceVariables;
    Map<String, Map<String, Variable>> localVariables;

    /**
     * Initializes a new instance of the {@link VariableTableImpl} test.
     */
    public VariableTableImpl() {
        this.all = new HashMap<String, List<Variable>>();
        this.globalVariables = new HashMap<String, Variable>();
        this.classVariables = new HashMap<String, Map<String, Variable>>();
        this.instanceVariables = new HashMap<String, Map<String, Variable>>();
        this.localVariables = new HashMap<String, Map<String, Variable>>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.cdiapi.VariableTable#clone()
     */
    @Override
    public VariableTable clone() {
        VariableTableImpl clone = new VariableTableImpl();
        clone.globalVariables = new HashMap<String, Variable>(this.globalVariables);

        for (Entry<String, List<Variable>> outerEntry : this.all.entrySet()) {
            ArrayList<Variable> newList = new ArrayList<Variable>(outerEntry.getValue());
            clone.all.put(outerEntry.getKey(), newList);
        }

        for (Entry<String, Map<String, Variable>> outerEntry : this.classVariables.entrySet()) {
            Map<String, Variable> innerMap = new HashMap<String, Variable>();
            clone.classVariables.put(outerEntry.getKey(), innerMap);
            for (Entry<String, Variable> innerEntry : outerEntry.getValue().entrySet()) {
                innerMap.put(innerEntry.getKey(), innerEntry.getValue());
            }
        }
        for (Entry<String, Map<String, Variable>> outerEntry : this.instanceVariables.entrySet()) {
            Map<String, Variable> innerMap = new HashMap<String, Variable>();
            clone.instanceVariables.put(outerEntry.getKey(), innerMap);
            for (Entry<String, Variable> innerEntry : outerEntry.getValue().entrySet()) {
                innerMap.put(innerEntry.getKey(), innerEntry.getValue());
            }
        }
        for (Entry<String, Map<String, Variable>> outerEntry : this.localVariables.entrySet()) {
            Map<String, Variable> innerMap = new HashMap<String, Variable>();
            clone.localVariables.put(outerEntry.getKey(), innerMap);
            for (Entry<String, Variable> innerEntry : outerEntry.getValue().entrySet()) {
                innerMap.put(innerEntry.getKey(), innerEntry.getValue());
            }
        }

        return clone;
    }

    /**
     * Puts a variable into the table. If a value already exists with the passed key, the value will be overwritten.
     * 
     * @param var
     *            The variable to put.
     */
    @Override
    public void update(ScopeIdentifier scopeId, Variable var) {
        ScopeType scopeType = Enum.valueOf(ScopeType.class, var.getOptions().get(SCOPE_TYPE_KEY));
        String definingClassId = var.getOptions().get(DEFINING_CLASS_ID_KEY);
        String definingInstanceId = var.getOptions().get(DEFINING_INSTANCE_ID_KEY);
        String localScopeId = scopeId.getScopeId();
        logger.debug("Putting Variable '" + var.getName() + "' defined in scope '" + scopeType + "'.");
        switch (scopeType) {
            case CLASS: {
                this.updateMap(this.classVariables, definingClassId, var);
                break;
            }
            case GLOBAL: {
                this.updateGlobalVariables(var);
                break;
            }
            case INSTANCE: {
                this.updateMap(this.instanceVariables, definingInstanceId, var);
                break;
            }
            case LOCAL: {
                this.updateMap(this.localVariables, localScopeId, var);
                break;
            }
            default:
                break;
        }
    }

    /**
     * Gets a list of {@link VariableImpl}s visible in a specified scope.
     * 
     * @return A list of {@link VariableImpl}s visible in the requested scope.
     */
    @Override
    public List<Variable> getVisibleInScope(ScopeIdentifier scopeId) {
        String classId = scopeId.getOptions().get(DEFINING_CLASS_ID_KEY);
        String instanceId = scopeId.getOptions().get(DEFINING_INSTANCE_ID_KEY);
        String localScopeId = scopeId.getScopeId();
        logger.debug("Finding all variables visible in classId '" + classId + "', instance id '" + instanceId
            + "', local scope '" + localScopeId + "'.");
        ArrayList<Variable> returnList = new ArrayList<Variable>();

        Map<String, Variable> classVars = this.classVariables.get(classId);
        if (classVars != null) {
            returnList.addAll(classVars.values());
        }

        Map<String, Variable> instanceVars = this.instanceVariables.get(instanceId);
        if (instanceVars != null) {
            returnList.addAll(instanceVars.values());
        }

        Map<String, Variable> localVariables = this.localVariables.get(localScopeId);
        if (localVariables != null) {
            returnList.addAll(localVariables.values());
        }

        returnList.addAll(this.globalVariables.values());
        return returnList;
    }

    /**
     * Gets all known variables.
     * 
     * @return Gets all known variables from all scopes.
     */
    @Override
    public List<Variable> getAll() {
        ArrayList<Variable> returnList = new ArrayList<Variable>();
        for (Entry<String, Variable> e : this.globalVariables.entrySet()) {
            returnList.add(e.getValue());
        }

        for (Entry<String, Map<String, Variable>> outerEntry : this.classVariables.entrySet()) {
            for (Entry<String, Variable> innerEntry : outerEntry.getValue().entrySet()) {
                returnList.add(innerEntry.getValue());
            }
        }

        for (Entry<String, Map<String, Variable>> outerEntry : this.instanceVariables.entrySet()) {
            for (Entry<String, Variable> innerEntry : outerEntry.getValue().entrySet()) {
                returnList.add(innerEntry.getValue());
            }
        }

        for (Entry<String, Map<String, Variable>> outerEntry : this.localVariables.entrySet()) {
            for (Entry<String, Variable> innerEntry : outerEntry.getValue().entrySet()) {
                returnList.add(innerEntry.getValue());
            }
        }

        return returnList;
    }

    /**
     * Drops all variables of a local scope from the table.
     * 
     */
    @Override
    public void dropScope(ScopeIdentifier scopeId) {
        String classId = scopeId.getOptions().get(DEFINING_CLASS_ID_KEY);
        if (classId != null) {
            this.classVariables.remove(classId);
        }

        String instanceId = scopeId.getOptions().get(DEFINING_INSTANCE_ID_KEY);
        if (instanceId != null) {
            this.instanceVariables.remove(instanceId);
        }

        String localScopeId = scopeId.getOptions().get(LOCAL_SCOPE_ID);
        if (localScopeId != null) {
            this.localVariables.remove(localScopeId);
        }
    }

    private void updateGlobalVariables(Variable var) {
        Variable dropMe = this.globalVariables.get(var.getName());
        this.removeFromAll(dropMe);
        this.globalVariables.put(var.getName(), var);
        this.addToAll(var);
        this.updateAllWithSameAddress(var.getAddress(), var.getValue());
    }

    private void updateMap(Map<String, Map<String, Variable>> map, String scope, Variable var) {
        Map<String, Variable> scopeMap = map.get(scope);
        if (scopeMap == null) {
            scopeMap = new HashMap<String, Variable>();
            map.put(scope, scopeMap);
        }
        Variable dropMe = scopeMap.get(var.getName());
        this.removeFromAll(dropMe);
        scopeMap.put(var.getName(), var);
        this.addToAll(var);
        this.updateAllWithSameAddress(var.getAddress(), var.getValue());
    }

    private void updateAllWithSameAddress(String address, String newValue) {
        logger.debug("Updating all variables at address '" + address + "' to value '" + newValue + "'.");
        List<Variable> updateUs = this.all.get(address);
        if (updateUs != null) {
            for (Variable var : updateUs) {
                var.setValue(newValue);
            }
        }
    }

    private void addToAll(Variable var) {
        List<Variable> sameAddress = this.all.get(var.getAddress());
        if (sameAddress == null) {
            sameAddress = new ArrayList<Variable>();
            this.all.put(var.getAddress(), sameAddress);
        }
        sameAddress.add(var);
    }

    private void removeFromAll(Variable var) {
        if (var != null) {
            List<Variable> sameAddress = this.all.get(var.getAddress());
            if (sameAddress != null) {
                sameAddress.remove(var);
            }
        }
    }

}
