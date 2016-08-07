package de.cau.lps.debugger.languagespecific.api;

/**
 * The abstract {@link VariableTable} factory. Must be subclassed by language specific implementations.
 * 
 * @author Thomas Ulrich
 */
public abstract class AbstractVariableTableFactory {

    /**
     * Creates a new {@link VariableTable}.
     * 
     * @return A new {@link VariableTable}.
     */
    public abstract VariableTable create();

}
