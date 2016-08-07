package de.cau.lps.debugger.program;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.common.VariableTableFactory;
import de.cau.lps.debugger.languagespecific.api.VariableTable;

/**
 * This class keeps the state of the Debugger, i.e. the current position, the call stack etc.
 * 
 * @author Thomas Ulrich
 */
public class DebuggerState {
    private Stack<Call> callStack;
    private VariableTable variableTable;
    private Position position;
    private boolean autoStep;
    private boolean stepOverNextCall;

    /**
     * Initializes a new instance of the {@link DebuggerState} class.
     * 
     */
    public DebuggerState() {
        this.callStack = new Stack<Call>();
        this.position = new Position();
        this.autoStep = false;
        this.stepOverNextCall = false;
        this.setVariableTable(VariableTableFactory.create());
    }

    /**
     * Initializes a new instance of the {@link DebuggerState} class, using another DebuggerState as a blueprint,
     * copying its state.
     * 
     * @param other
     *            The other {@link DebuggerState} object.
     */
    public DebuggerState(DebuggerState other) {
        this();
        if (other != null) {
            this.callStack.addAll(other.getCallStack());
            this.variableTable = other.getVariableTable().clone();
            this.position = new Position(other.getPosition());
            this.autoStep = other.isAutoStep();
            this.stepOverNextCall = other.isStepOverNextCall();
        }
    }

    /**
     * Gets the Call Stack.
     * 
     * @return A Stack of {@link Call}s.
     */
    public Stack<Call> getCallStack() {
        return callStack;
    }

    /**
     * Gets the VariableTable.
     * 
     * @return A {@link VariableTable} object.
     */
    public VariableTable getVariableTable() {
        return variableTable;
    }

    /**
     * Sets the {@link VariableTable}.
     * 
     * @param table
     *            The new {@link VariableTable}.
     */
    public void setVariableTable(VariableTable table) {
        this.variableTable = table;
    }

    /**
     * Gets the current position.
     * 
     * @return A {@link Position} object.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Informs the debugger of the new position within the source code.
     * 
     * @param newPosition
     *            The {@link Position}.
     */
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    /**
     * Returns the value of the autoStep flag.
     * 
     * @return True if the debugger auto-acknowledges messages from the runtime, otherwise false.
     */
    public boolean isAutoStep() {
        return autoStep;
    }

    /**
     * Sets the autoStep flag, i.e. enabling or disabling auto stepping.
     * 
     * @param autoStep
     *            True if auto stepping should be enabled, false if it should be disabled.
     */
    public void setAutoStep(boolean autoStep) {
        this.autoStep = autoStep;
    }

    /**
     * Returns the value of the stepOverNextCall flag.
     * 
     * @return True if debugger is supposed to step over next call, otherwise false.
     */
    public boolean isStepOverNextCall() {
        return stepOverNextCall;
    }

    /**
     * Sets the stepOverNextCall flag.
     * 
     * @param stepOverNextCall
     *            True if next call should be stepped over, false if not.
     */
    public void setStepOverNextCall(boolean stepOverNextCall) {
        this.stepOverNextCall = stepOverNextCall;
    }

    /**
     * Gets the Map of options for the topmost call or an empty map if call stack is empty.
     * 
     * @return A {@link Map} of String keys and String values.
     */
    public Map<String, String> getTopMostCallOptions() {
        return this.callStack.empty() ? new HashMap<String, String>() : this.callStack.peek().getOptions();
    }

    /**
     * Gets the identifier of the current scope.
     * 
     * @return The current scope identifier.
     */
    public String getLocalScopeIdentifier() {
        return this.callStack.empty() ? "DEFAULT" : this.callStack.peek().getAssociatedPushMessageID();
    }

}