package de.cau.lps.debugger.languagespecific.initialization;

import de.cau.lps.debugger.common.VariableTableFactory;
import de.cau.lps.debugger.exception.NoFactorySetException;
import de.cau.lps.debugger.languagespecific.ruby.VariableTableFactoryImpl;

/**
 * Initializes dependencies.
 * 
 * @author Thomas Ulrich
 */
public class DependencyInitializer {

    /**
     * Sets language specific objects. This is where you can wire in your own implementations of language specific
     * classes.
     * 
     * @throws NoFactorySetException
     *             Thrown if a required factory is not set.
     */
    public static void initialize() throws NoFactorySetException {
        VariableTableFactory.setConcreteFactory(new VariableTableFactoryImpl());

        checkInitialization();
    }

    /**
     * Checks if initialization was successful.
     * 
     * @throws NoFactorySetException
     *             Thrown if a required factory is not set.
     */
    public static void checkInitialization() throws NoFactorySetException {
        if (!VariableTableFactory.isFactorySet()) {
            throw new NoFactorySetException("No concrete variable table factory set.");
        }
    }
}
