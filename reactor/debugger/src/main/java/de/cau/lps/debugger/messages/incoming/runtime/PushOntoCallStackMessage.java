package de.cau.lps.debugger.messages.incoming.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * Message used to signal the pushing of a call onto the call stack.
 * 
 * @author Thomas Ulrich
 *
 */
public class PushOntoCallStackMessage extends AbstractMessage {

    private String methodName;
    private String expectedFormat;

    private Map<String, String> options;
    private List<String> arguments;

    /**
     * Initializes a new instance of the {@link PushOntoCallStackMessage} class.
     * 
     * @param message
     *            The String containing the 'Push this call onto call stack' message.
     * @throws MessageWronglyFormattedException
     *             Thrown if message is wrongly formatted.
     */
    public PushOntoCallStackMessage(String message) throws MessageWronglyFormattedException {

        this.arguments = new ArrayList<String>();
        this.options = new HashMap<String, String>();

        StringBuilder builder = new StringBuilder();

        builder.append(IncomingMessageType.PUSHONTOCALLSTACK.toString());
        builder.append(MessageFormatter.DELIMITER);
        builder.append("<MethodName>");
        builder.append(MessageFormatter.DELIMITER);
        builder.append("<optional Argument>, repeat as desired separated by semicolon");
        builder.append(MessageFormatter.DELIMITER);
        builder.append("<optional Key=*=Value, repeat as desired separated by semicolon>");
        this.expectedFormat = builder.toString();

        String[] splitted = message.split(MessageFormatter.DELIMITER);
        if (splitted.length < 2) {
            throw new MessageWronglyFormattedException(message, this.expectedFormat);
        }

        this.methodName = splitted[1];
        if (splitted.length > 2) {
            String regex = MessageFormatter.ASSIGN_SYMBOL.replace("*", "\\*");
            for (int i = 2; i < splitted.length; i++) {
                if (splitted[i].contains(MessageFormatter.ASSIGN_SYMBOL)) {
                    String[] innerSplit = splitted[i].split(regex);
                    this.options.put(innerSplit[0], innerSplit[1]);
                } else {
                    this.arguments.add(splitted[i]);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append(IncomingMessageType.PUSHONTOCALLSTACK + MessageFormatter.DELIMITER);
        builder.append(this.methodName);

        for (String argument : this.arguments) {
            builder.append(MessageFormatter.DELIMITER);
            builder.append(argument);
        }
        for (Entry<String, String> entry : this.options.entrySet()) {
            builder.append(MessageFormatter.DELIMITER);
            builder.append(entry.getKey() + MessageFormatter.ASSIGN_SYMBOL + entry.getValue());
        }

        return builder.toString();
    }

    /**
     * Gets the name of the method that has been called and pushed onto the call stack.
     * 
     * @return The method name.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Gets the list of arguments.
     * 
     * @return A list of strings representing the arguments.
     */
    public List<String> getArguments() {
        return arguments;
    }

    /**
     * Gets the {@link Map} of additional options.
     * 
     * @return The Map.
     */
    public Map<String, String> getOptions() {
        return options;
    }

}
