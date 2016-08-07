package de.cau.lps.debugger.commonMocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.languagespecific.api.VariableTable;

/**
 * Mocks out the {@link VariableTable} class for test purposes.
 * 
 * @author Thomas Ulrich
 */
public class VariableTableMock extends VariableTable {

    private Map<String, Variable> varMap;

    /**
     * Initializes a new instance of the VariableTableMock.
     */
    public VariableTableMock() {
        this.varMap = new HashMap<String, Variable>();
    }

    @Override
    public void update(ScopeIdentifier scopeId, Variable var) {
        this.varMap.put(scopeId.getScopeId(), var);
    }

    @Override
    public List<Variable> getVisibleInScope(ScopeIdentifier scopeId) {
        return Arrays.asList(this.varMap.get(scopeId.getScopeId()));
    }

    @Override
    public List<Variable> getAll() {
        return this.varMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

    @Override
    public void dropScope(ScopeIdentifier scopeId) {
        this.varMap.remove(scopeId.getScopeId());
    }

    @Override
    public VariableTable clone() {
        VariableTableMock mock = new VariableTableMock();
        for (Entry<String, Variable> e : this.varMap.entrySet()) {
            mock.varMap.put(e.getKey(), e.getValue());
        }
        return mock;
    }
}
