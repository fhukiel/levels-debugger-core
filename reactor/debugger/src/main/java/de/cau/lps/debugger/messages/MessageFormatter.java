package de.cau.lps.debugger.messages;

/**
 * Contains formatting information.
 * 
 * @author Thomas Ulrich
 *
 */
public abstract class MessageFormatter {

    /**
     * The symbol that is used to delimit parts of a message.
     */
    public static final String DELIMITER = ";";

    /**
     * The symbol used to signal a variable assignment in a message.
     */
    public static final String ASSIGN_SYMBOL = "=*=";
}
