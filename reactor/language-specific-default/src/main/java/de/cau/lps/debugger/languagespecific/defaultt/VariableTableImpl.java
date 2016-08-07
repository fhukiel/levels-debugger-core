package de.cau.lps.debugger.languagespecific.defaultt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.languagespecific.api.VariableTable;

/**
 * A default implementation of the {@link VariableTable}. This implementation only handles local scopes.
 * 
 * @author Thomas Ulrich
 */
public class VariableTableImpl extends VariableTable {

    private Map<String, Map<String, Variable>> scopes;

    /**
     * Initializes a new instance of the {@link VariableTableImpl} class.
     */
    public VariableTableImpl() {
        this.scopes = new HashMap<String, Map<String, Variable>>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.languagespecific.api.VariableTable#update(de.cau.lps.debugger.languagespecific.api.
     * ScopeIdentifier, de.cau.lps.debugger.languagespecific.api.Variable)
     */
    @Override
    public void update(ScopeIdentifier scopeId, Variable var) {
        Map<String, Variable> scopeTable = this.scopes.get(scopeId.getScopeId());
        if (scopeTable == null) {
            scopeTable = new HashMap<String, Variable>();
            this.scopes.put(scopeId.getScopeId(), scopeTable);
        }
        scopeTable.put(var.getName(), var);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.cau.lps.debugger.languagespecific.api.VariableTable#getVisibleInScope(de.cau.lps.debugger.languagespecific.api
     * .ScopeIdentifier)
     */
    @Override
    public List<Variable> getVisibleInScope(ScopeIdentifier scopeId) {
        ArrayList<Variable> returnList = new ArrayList<Variable>();
        Map<String, Variable> scopeTable = this.scopes.get(scopeId.getScopeId());
        if (scopeTable != null) {
            for (Entry<String, Variable> e : scopeTable.entrySet()) {
                returnList.add(e.getValue());
            }
        }
        return returnList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.languagespecific.api.VariableTable#getAll()
     */
    @Override
    public List<Variable> getAll() {
        ArrayList<Variable> returnList = new ArrayList<Variable>();
        for (Entry<String, Map<String, Variable>> outerEntry : this.scopes.entrySet()) {
            for (Entry<String, Variable> innerEntry : outerEntry.getValue().entrySet()) {
                returnList.add(innerEntry.getValue());
            }
        }
        return returnList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.languagespecific.api.VariableTable#dropScope(de.cau.lps.debugger.languagespecific.api.
     * ScopeIdentifier)
     */
    @Override
    public void dropScope(ScopeIdentifier scopeId) {
        this.scopes.remove(scopeId.getScopeId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.languagespecific.api.VariableTable#clone()
     */
    @Override
    public VariableTable clone() {
        VariableTableImpl clone = new VariableTableImpl();
        clone.scopes = new HashMap<String, Map<String, Variable>>(this.scopes);
        return clone;
    }
}
