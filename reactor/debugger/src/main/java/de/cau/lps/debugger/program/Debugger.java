package de.cau.lps.debugger.program;

import java.util.Set;

import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.exception.MissingReplayInformationException;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.messages.AbstractMessage;

/**
 * This interface must be implemented by any class containing the actual debugging logic.
 * 
 * @author Thomas Ulrich
 */
public interface Debugger {

    /**
     * Records an {@link AbstractMessage} for later replay.
     * 
     * @param message
     *            The message to record.
     */
    void recordMessage(AbstractMessage message);

    /**
     * Pushes a call onto the call stack, takes a snapshot of the current configuration and sends the updated call stack
     * to the view.
     * 
     * @param call
     *            The {@link Call} to push onto the stack.
     */
    void pushOntoCallStack(Call call);

    /**
     * Pops the topmost {@link Call} from the call stack.
     * 
     * @return The popped {@link Call}.
     */
    Call popFromCallStack();

    /**
     * Updates the variable table by putting a variable name and a value into the table, possibly overwriting an
     * existing value.
     * 
     * @param variable
     *            The {@link Variable} to put.
     */
    void updateTable(Variable variable);

    /**
     * Replays recorded runtime from a previous call onwards.
     * 
     * @param replayFromCallId
     *            The ID of the {@link Call} to replay from.
     * @throws MissingReplayInformationException
     *             Thrown if no replay information is found, i.e. no messages or no message log.
     */
    void startReplay(String replayFromCallId) throws MissingReplayInformationException;

    /**
     * Stops the replay of previously recorded messages.
     */
    void stopReplay();

    /**
     * Informs the debugger of the new position within the source code.
     * 
     * @param newPosition
     *            The {@link Position}.
     */
    void setPosition(Position newPosition);

    /**
     * Sends a step signal to the runtime.
     */
    void sendStepSignalToRuntime();

    /**
     * Signals the end of the replay tape to the view.
     */
    void signalEndOfReplayTape();

    /**
     * Enables auto-stepping, i.e. 'STEP' signal is sent to the runtime automatically.
     */
    void enableAutoStepping();

    /**
     * Disables auto-stepping, i.e. 'STEP' signal is no longer sent to the runtime automatically.
     */
    void disableAutoStepping();

    /**
     * Gets the inner {@link DebuggerState}.
     * 
     * @return The {@link DebuggerState} object.
     */
    DebuggerState getState();

    /**
     * Gets the set of breakpoints.
     * 
     * @return A Set of {@link Position}s.
     */
    Set<Position> getBreakPoints();

    /**
     * Checks if a given position is a breakpoint.
     * 
     * @param position
     *            The position to check.
     * @return True if position is in set of breakpoints.
     */
    boolean isBreakpoint(Position position);

    /**
     * Sets the breakpoints enabled flag.
     * 
     * @param breakpointsEnabled
     *            True if breakpoints should be enabled, otherwise false.
     */
    void setBreakpointsEnabled(boolean breakpointsEnabled);

    /**
     * Gets the breakpoints enabled flag.
     * 
     * @return True if breakpoints are enabled, otherwise false.
     */
    boolean isBreakpointsEnabled();
}