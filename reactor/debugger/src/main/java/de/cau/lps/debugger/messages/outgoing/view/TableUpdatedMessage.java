package de.cau.lps.debugger.messages.outgoing.view;

import java.util.List;

import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.languagespecific.api.VariableTable;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.program.DebuggerImpl;

/**
 * Message used to signal the updating of the variable table. Message originates from the {@link DebuggerImpl} and is
 * sent to the view.
 * 
 * @author Thomas Ulrich
 *
 */
public class TableUpdatedMessage extends AbstractMessage {

    private List<Variable> variables;

    /**
     * Initializes a new instance of the {@link TableUpdatedMessage} class.
     * 
     * @param variables
     *            A list of variables contained in this {@link VariableTable}.
     */
    public TableUpdatedMessage(List<Variable> variables) {
        this.variables = variables;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();

        builder.append(OutgoingMessageType.TABLEUPDATED);
        for (Variable variable : this.variables) {
            builder.append(MessageFormatter.DELIMITER + variable.getName() + MessageFormatter.ASSIGN_SYMBOL
                + variable.getValue() + MessageFormatter.ASSIGN_SYMBOL + variable.getAddress());
        }

        return builder.toString();
    }
}
