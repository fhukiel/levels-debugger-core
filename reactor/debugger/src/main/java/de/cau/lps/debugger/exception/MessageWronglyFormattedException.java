package de.cau.lps.debugger.exception;

/**
 * This exception is thrown when a message String does not match the expected format.
 * 
 * @author Thomas Ulrich
 *
 */
public class MessageWronglyFormattedException extends Exception {

    private static final long serialVersionUID = 6479492282921348845L;

    /**
     * Initializes a new instance of the MessageWronglyFormattedException class.
     * 
     * @param presented
     *            The wrongly formatted message.
     * @param expected
     *            The expected format.
     */
    public MessageWronglyFormattedException(String presented, String expected) {
        super("Message '" + presented + "' does not match expected format '" + expected + "'.");
    }

    /**
     * Initializes a new instance of the {@link MessageWronglyFormattedException} class.
     * 
     * @param cause
     *            An inner exception wrapped by this {@link MessageWronglyFormattedException}.
     */
    public MessageWronglyFormattedException(Throwable cause) {
        super(cause);
    }
}
