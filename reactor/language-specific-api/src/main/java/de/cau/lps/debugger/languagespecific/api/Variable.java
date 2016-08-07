package de.cau.lps.debugger.languagespecific.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a variable.
 * 
 * @author Thomas Ulrich
 */
public class Variable {

    private String name;
    private String value;
    private String address;
    private Map<String, String> options;

    /**
     * Initializes a new instance of the {@link Variable} class.
     * 
     * @param name
     *            The name of the variable.
     * @param value
     *            The value of the variable.
     * @param address
     *            The address of the variable. The class instance (=object) this Variable was defined in.
     * @param options
     *            A {@link Map} of Strings keys and String values containing further options.
     */
    public Variable(String name, String value, String address, Map<String, String> options) {
        this.name = name;
        this.value = value;
        this.address = address;
        this.options = new HashMap<String, String>(options);
    }

    /**
     * Initializes a new instance of the {@link Variable} class.
     * 
     * @param other
     *            Another variable to copy.
     */
    public Variable(Variable other) {
        this.name = new String(other.name);
        this.value = new String(other.value);
        this.address = new String(other.address);
        this.options = new HashMap<String, String>(other.options);
    }

    /**
     * Gets this {@link Variable}'s name.
     * 
     * @return The {@link Variable} name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets this {@link Variable}'s name.
     * 
     * @param name
     *            The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets this {@link Variable}'s value.
     * 
     * @return The {@link Variable} value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets this {@link Variable}'s value.
     * 
     * @param value
     *            The new value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets this {@link Variable}'s address.
     * 
     * @return The {@link Variable} value.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets this {@link Variable}'s address.
     * 
     * @param address
     *            The new address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets this {@link Variable}'s options.
     * 
     * @return A {@link Map} of String keys and String values.
     */
    public Map<String, String> getOptions() {
        return this.options;
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
