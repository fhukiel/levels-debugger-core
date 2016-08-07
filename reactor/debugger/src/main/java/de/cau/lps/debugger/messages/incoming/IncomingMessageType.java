package de.cau.lps.debugger.messages.incoming;

/**
 * Lists the known incoming message types.
 * 
 * @author Thomas Ulrich
 *
 */
public enum IncomingMessageType {
    /**
     * Incoming message signals the adding of a breakpoint.
     */
    ADDBREAKPOINT,

    /**
     * Incoming message signals that there are no more messages to replay.
     */
    ENDOFTAPE,

    /**
     * Incoming message signals that all breakpoints should be enabled.
     */
    ENABLEALLBREAKPOINTS,

    /**
     * Incoming message signals that all breakpoints should be disabled.
     */
    DISABLEALLBREAKPOINTS,

    /**
     * Incoming message signals the removal of a breakpoint.
     */
    REMOVEBREAKPOINT,

    /**
     * Incoming message signals the removal of all breakpoints.
     */
    REMOVEALLBREAKPOINTS,

    /**
     * Incoming message signals the request to step to next statement.
     */
    STEP,

    /**
     * Incoming message signals the request to step over next statement.
     */
    STEPOVER,

    /**
     * Incoming message signals the request to run to next breakpoint.
     */
    RUNTONEXTBREAKPOINT,

    /**
     * Incoming message signals the request to run to the end of the current method.
     */
    RUNTOENDOFMETHOD,

    /**
     * Incoming message signals the request to update the variable table.
     */
    UPDATETABLE,

    /**
     * Incoming message signals the request to update the current position.
     */
    UPDATEPOSITION,

    /**
     * Incoming message signals the request to push a new method onto the call stack.
     */
    PUSHONTOCALLSTACK,

    /**
     * Incoming message signals the request to pop a method call from the call stack.
     */
    POPFROMCALLSTACK,

    /**
     * Incoming message signals the request to start a replay from a certain point in time.
     */
    STARTREPLAY,

    /**
     * Incoming message signals the request to stop replay.
     */
    STOPREPLAY
}
