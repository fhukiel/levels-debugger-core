package de.cau.lps.debugger.exception;

/**
 * This exception is thrown when an unknown message type is detected.
 * 
 * @author Thomas Ulrich
 *
 */
public class UnknownMessageTypeException extends Exception {

    private static final long serialVersionUID = 1832841512604775232L;

    /**
     * Initializes a new instance of the UnknownMessageTypeException.
     * 
     * @param error
     *            The error message to encapsulate in this exception.
     */
    public UnknownMessageTypeException(String error) {
        super("Unknown message type '" + error + "'.");
    }
}
