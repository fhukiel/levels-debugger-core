package de.cau.lps.debugger.exception;

/**
 * This exception is thrown when an error occurs during the conversion of a String to a concrete Message object.
 * 
 * @author Thomas Ulrich
 *
 */
public class MessageConversionException extends Exception {

    private static final long serialVersionUID = 5319234329182717538L;

    /**
     * Initializes a new instance of the MessageConversionException.
     * 
     * @param error
     *            The error message to encapsulate in this exception.
     */
    public MessageConversionException(String error) {
        super(error);
    }
}
