package de.cau.lps.debugger.common;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages breakpoints.
 * 
 * @author Thomas Ulrich
 */
public class BreakpointManager {

    private Set<Position> breakpoints;
    private boolean breakpointsEnabled;

    /**
     * Initializes a new instance of the BreakpointManager class.
     */
    public BreakpointManager() {
        this.breakpoints = new HashSet<Position>();
        this.breakpointsEnabled = true;
    }

    /**
     * Removes all breakpoints.
     */
    public void removeAllBreakpoints() {
        this.breakpoints = new HashSet<Position>();
    }

    /**
     * Checks if there's a breakpoint at a given {@link Position}.
     * 
     * @param position
     *            The {@link Position} to check.
     * @return True if there's a breakpoint at the given position, otherwise false.
     */
    public boolean isBreakpoint(Position position) {
        if (this.breakpointsEnabled) {
            return this.breakpoints.stream().filter(bp -> bp.getLine() == position.getLine()).findFirst().isPresent();
        }

        return false;
    }

    /**
     * Gets all known breakpoints.
     * 
     * @return A Set of {@link Position}s representing the breakpoints.
     */
    public Set<Position> getAll() {
        return this.breakpoints;
    }

    /**
     * Gets a flag indicating whether breakpoints are enabled or not.
     * 
     * @return True if breakpoints are enabled, otherwise false.
     */
    public boolean isBreakpointsEnabled() {
        return breakpointsEnabled;
    }

    /**
     * Sets a flag turning breakpoints on or off.
     * 
     * @param breakpointsEnabled
     *            True if breakpoints are to be enabled, otherwise false.
     */
    public void setBreakpointsEnabled(boolean breakpointsEnabled) {
        this.breakpointsEnabled = breakpointsEnabled;
    }
}
