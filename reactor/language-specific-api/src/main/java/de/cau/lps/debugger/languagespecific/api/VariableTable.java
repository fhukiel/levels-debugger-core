package de.cau.lps.debugger.languagespecific.api;

import java.util.List;

/**
 * An abstract class used for storing {@link Variable}s.
 * 
 * @author Thomas Ulrich
 */
public abstract class VariableTable {

    /**
     * Initializes a new instance of the {@link VariableTable} class.
     */
    public VariableTable() {
    }

    /**
     * Initializes a new instance of the {@link VariableTable} class based on another {@link VariableTable}.
     * 
     * @param other
     *            The other {@link VariableTable}.
     */
    public VariableTable(VariableTable other) {
    }

    /**
     * Creates or updates a {@link Variable}.
     * 
     * @param scopeId
     *            The scope within which the {@link Variable} lives.
     * @param var
     *            The {@link Variable}.
     */
    public abstract void update(ScopeIdentifier scopeId, Variable var);

    /**
     * Gets the list of {@link Variable}s visible within a scope as identified by a {@link ScopeIdentifier}.
     * 
     * @param scopeId
     *            The {@link ScopeIdentifier} identifying the scope.
     * @return A List of {@link Variable}s visible within the scope.
     */
    public abstract List<Variable> getVisibleInScope(ScopeIdentifier scopeId);

    /**
     * Gets all known {@link Variable}s.
     * 
     * @return A list of {@link Variable}s.
     */
    public abstract List<Variable> getAll();

    /**
     * Drops a scope and all its variables.
     * 
     * @param scopeId
     *            The scope to drop.
     */
    public abstract void dropScope(ScopeIdentifier scopeId);

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    public abstract VariableTable clone();
}
