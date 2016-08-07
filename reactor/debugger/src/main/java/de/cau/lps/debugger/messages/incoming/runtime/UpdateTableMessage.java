package de.cau.lps.debugger.messages.incoming.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;

/**
 * A message used to signal the change of a variable. Message originates from the runtime.
 * 
 * @author Thomas Ulrich
 *
 */
public class UpdateTableMessage extends AbstractMessage {

    private String variableName;
    private String variableValue;
    private String variableAddress;
    private Map<String, String> options;
    private String expectedFormat;

    /**
     * Initializes a new instance of the {@link UpdateTableMessage} class.
     * 
     * @param message
     *            The String containing the 'Set variable X to value Y in table'.
     * @throws MessageWronglyFormattedException
     *             Thrown if passed message is wrongly formatted.
     */
    public UpdateTableMessage(String message) throws MessageWronglyFormattedException {

        this.options = new HashMap<String, String>();
        StringBuilder builder = new StringBuilder();
        builder.append(IncomingMessageType.UPDATETABLE.toString());
        builder.append(MessageFormatter.DELIMITER);
        builder.append("<VariableName>");
        builder.append(MessageFormatter.DELIMITER);
        builder.append("<VariableValue>");
        builder.append(MessageFormatter.DELIMITER);
        builder.append("<VariableAddress>");
        builder.append(MessageFormatter.DELIMITER);
        builder.append("<Key>=*=<Value> (repeat as desired, separate key/values pairs with semicolon (;))");
        this.expectedFormat = builder.toString();

        String[] splitted = message.split(MessageFormatter.DELIMITER);

        if (splitted.length < 4) {
            throw new MessageWronglyFormattedException(message, this.expectedFormat);
        }

        this.variableName = splitted[1];
        this.variableValue = splitted[2];
        this.variableAddress = splitted[3];
        if (splitted.length > 4) {
            String regex = MessageFormatter.ASSIGN_SYMBOL.replace("*", "\\*");
            for (int i = 4; i < splitted.length; i++) {
                String[] innerSplit = splitted[i].split(regex);
                if (innerSplit.length != 2) {
                    throw new MessageWronglyFormattedException(message, this.expectedFormat);
                }
                this.options.put(innerSplit[0], innerSplit[1]);
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
        builder.append(IncomingMessageType.UPDATETABLE + MessageFormatter.DELIMITER);
        builder.append(this.variableName + MessageFormatter.DELIMITER);
        builder.append(this.variableValue + MessageFormatter.DELIMITER);
        builder.append(this.variableAddress);

        for (Entry<String, String> entry : this.options.entrySet()) {
            builder.append(MessageFormatter.DELIMITER);
            builder.append(entry.getKey());
            builder.append(MessageFormatter.ASSIGN_SYMBOL);
            builder.append(entry.getValue());
        }
        return builder.toString();
    }

    /**
     * Gets the name of the updated variable.
     * 
     * @return The variable name.
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * Gets the value of the updated variable.
     * 
     * @return The variable value.
     */
    public String getVariableValue() {
        return variableValue;
    }

    /**
     * Gets the address of the updated variable.
     * 
     * @return The variable address.
     */
    public String getVariableAddress() {
        return variableAddress;
    }

    /**
     * Gets a map of String keys and String values containing additional options.
     * 
     * @return The {@link Map}.
     */
    public Map<String, String> getOptions() {
        return options;
    }
}
