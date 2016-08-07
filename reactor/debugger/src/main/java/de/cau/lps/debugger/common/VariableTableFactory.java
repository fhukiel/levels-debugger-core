package de.cau.lps.debugger.common;

import de.cau.lps.debugger.languagespecific.api.AbstractVariableTableFactory;
import de.cau.lps.debugger.languagespecific.api.VariableTable;

/**
 * Creates {@link VariableTable} instances.
 * 
 * @author Thomas Ulrich
 */
public class VariableTableFactory {

    private AbstractVariableTableFactory factory;
    private static VariableTableFactory instance;

    /**
     * Sets the language specific {@link AbstractVariableTableFactory}.
     * 
     * @param concrete
     *            The concrete implementation of {@link AbstractVariableTableFactory}.
     */
    public static void setConcreteFactory(AbstractVariableTableFactory concrete) {
        getInstance();
        instance.factory = concrete;
    }

    /**
     * Creates a new {@link VariableTable} implementation.
     * 
     * @return A {@link VariableTable}.
     */
    public static VariableTable create() {
        return getInstance().factory.create();
    }

    /**
     * Checks if a concrete factory is set.
     * 
     * @return True if concrete factory is set, otherwise false.
     */
    public static boolean isFactorySet() {
        return getInstance().factory != null;
    }

    private static VariableTableFactory getInstance() {
        if (instance == null) {
            instance = new VariableTableFactory();
        }

        return instance;
    }
}
