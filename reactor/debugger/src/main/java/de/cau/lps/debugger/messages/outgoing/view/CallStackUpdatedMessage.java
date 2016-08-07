package de.cau.lps.debugger.messages.outgoing.view;

import java.util.List;
import java.util.Stack;

import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.program.DebuggerImpl;

/**
 * Message used to signal the updating of the CallStack. Message originates from the {@link DebuggerImpl} and is sent to
 * the View.
 * 
 * @author Thomas Ulrich
 *
 */
public class CallStackUpdatedMessage extends AbstractMessage {

    Stack<Call> callStack;

    /**
     * Initializes a new instance of the {@link CallStackUpdatedMessage} class.
     * 
     * @param callStack
     *            A Stack of {@link Call}s.
     */
    public CallStackUpdatedMessage(Stack<Call> callStack) {
        this.callStack = new Stack<Call>();
        this.callStack.addAll(callStack);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.messages.AbstractMessage#getStringRepresentation()
     */
    @Override
    protected String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append(OutgoingMessageType.CALLSTACKUPDATED);
        callStack.stream()
            .forEach(s -> builder
                .append(MessageFormatter.DELIMITER + s.getMethodName() + this.argumentsToString(s.getArguments())
                    + MessageFormatter.ASSIGN_SYMBOL + s.getAssociatedPushMessageID()));
        return builder.toString();
    }

    private String argumentsToString(List<String> arguments) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (int i = 0; i < arguments.size(); i++) {
            builder.append(arguments.get(i));
            if (i < arguments.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(")");
        return builder.toString();
    }
}
