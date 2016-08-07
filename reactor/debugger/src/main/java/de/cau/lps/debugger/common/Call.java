package de.cau.lps.debugger.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a method call.
 * 
 * @author Thomas Ulrich
 *
 */
public class Call {

    private String methodName;
    private List<String> arguments;
    private Map<String, String> options;
    private boolean marked;
    private String associatedPushMessageID;

    /**
     * Initializes a new instance of the {@link Call} class.
     * 
     * @param methodName
     *            Name of the method that has been called.
     * @param arguments
     *            A list of String representing the call arguments. The ID of the class this method was defined in.
     * @param associatedPushMessageID
     *            The ID of the {@link PushOntoCallStackMessage} that this call is related to.
     * @param options
     *            A map of String keys and String values of additional information.
     */
    public Call(String methodName, List<String> arguments, String associatedPushMessageID,
        Map<String, String> options) {
        this.methodName = methodName;
        this.arguments = arguments;
        this.associatedPushMessageID = associatedPushMessageID;
        this.marked = false;
        this.options = new HashMap<String, String>(options);
    }

    /**
     * Gets the name of the method that has been called.
     * 
     * @return The method name.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Gets the list of arguments for this call.
     * 
     * @return A List of strings representing the arguments.
     */
    public List<String> getArguments() {
        return arguments;
    }

    /**
     * Returns a flag indicating whether this call is being observed by the {@link DebuggerImpl}.
     * 
     * @return True if {@link DebuggerImpl} observes this call, otherwise false.
     */
    public boolean isMarked() {
        return marked;
    }

    /**
     * Sets the marked flag.
     * 
     * @param marked
     *            True if this call should be marked, false if not.
     */
    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    /**
     * Gets the ID of the associated {@link PushOntoCallStackMessage}.
     * 
     * @return The ID of the associated {@link PushOntoCallStackMessage}.
     */
    public String getAssociatedPushMessageID() {
        return associatedPushMessageID;
    }

    /**
     * Gets the Map of options.
     * 
     * @return The {@link Map}.
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
