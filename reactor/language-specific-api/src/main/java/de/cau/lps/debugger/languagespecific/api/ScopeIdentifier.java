package de.cau.lps.debugger.languagespecific.api;

import java.util.HashMap;
import java.util.Map;

/**
 * This class wraps information defining a variable scope.
 * 
 * @author Thomas Ulrich
 */
public class ScopeIdentifier {

    private String scopeId;
    private Map<String, String> options;

    /**
     * Initializes a new instance of the {@link ScopeIdentifier} class.
     * 
     * @param scopeId
     *            The ID of the scope identified by this identifier.
     */
    public ScopeIdentifier(String scopeId) {
        this.scopeId = scopeId;
        this.options = new HashMap<String, String>();
    }

    /**
     * Initializes a new instance of the {@link ScopeIdentifier} class.
     * 
     * @param scopeId
     *            The ID of the scope identified by this identifier.
     * @param options
     *            A {@link Map} of String, String containing additional options.
     */
    public ScopeIdentifier(String scopeId, Map<String, String> options) {
        this(scopeId);
        this.options = options;
    }

    /**
     * Gets the scope id.
     * 
     * @return The scope id.
     */
    public String getScopeId() {
        return scopeId;
    }

    /**
     * Sets the scope id.
     * 
     * @param scopeId
     *            The new scope id.
     */
    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    /**
     * Gets the {@link Map} of options.
     * 
     * @return The {@link Map} of options.
     */
    public Map<String, String> getOptions() {
        return options;
    }

    /**
     * Sets the {@link Map} of options.
     * 
     * @param options
     *            The new options.
     */
    public void setOptions(Map<String, String> options) {
        this.options = options;
    }
}
