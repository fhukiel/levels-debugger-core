package de.cau.lps.debugger.exception;

/**
 * Thrown if no replay information is found when the user requests to replay recorded messages.
 * 
 * @author Thomas Ulrich
 */
public class MissingReplayInformationException extends Exception {

    private static final long serialVersionUID = 6111218752607667792L;

    /**
     * Initializes a new instance of the {@link MissingReplayInformationException} class.
     * 
     * @param message
     *            The string message to be encapsulated in the object.
     */
    public MissingReplayInformationException(String message) {
        super(message);
    }

}
