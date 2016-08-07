package de.cau.lps.debugger.exception;

/**
 * This type of exception is thrown when a required factory has not been set.
 * 
 * @author Thomas Ulrich
 */
public class NoFactorySetException extends Exception {

    private static final long serialVersionUID = 6984945345092125507L;

    /**
     * Initializes a new instance of the {@link NoFactorySetException} class.
     * 
     * @param message
     *            The message to display.
     */
    public NoFactorySetException(String message) {
        super(message);
    }

}
