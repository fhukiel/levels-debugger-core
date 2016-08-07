package de.cau.lps.debugger.languagespecific.ruby;


/**
 * Defines the known scope types.
 * 
 * @author Thomas Ulrich
 */
public enum ScopeType {

    /**
     * The global scope.
     */
    GLOBAL,

    /**
     * A local scope.
     */
    LOCAL,

    /**
     * An instance scope.
     */
    INSTANCE,

    /**
     * A class scope.
     */
    CLASS

}
