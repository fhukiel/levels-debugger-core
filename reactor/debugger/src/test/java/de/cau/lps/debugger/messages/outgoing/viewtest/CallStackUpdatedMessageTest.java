package de.cau.lps.debugger.messages.outgoing.viewtest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.view.CallStackUpdatedMessage;

/**
 * Tests the {@link CallStackUpdatedMessage} class.
 */
public class CallStackUpdatedMessageTest {

    private static Stack<Call> stack;
    private static String methodAName;
    private static String methodBName;
    private static String methodCName;
    private static String methodAArgument1;
    private static String methodAArgument2;
    private static String methodBArgument;
    private static String methodCArgument;
    private static String methodAId;
    private static String methodBId;
    private static String methodCId;
    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        methodAName = "MethodA";
        methodBName = "MethodB";
        methodCName = "MethodC";
        methodAArgument1 = "aArg1";
        methodAArgument2 = "aArg2";
        methodBArgument = "bArg";
        methodCArgument = "cArg";
        methodAId = "1";
        methodBId = "2";
        methodCId = "3";

        stack = new Stack<Call>();
        stack.push(new Call(methodAName, Arrays.asList(methodAArgument1, methodAArgument2), methodAId,
            new HashMap<String, String>()));
        stack.push(new Call(methodBName, Arrays.asList(methodBArgument), methodBId, new HashMap<String, String>()));
        stack.push(new Call(methodCName, Arrays.asList(methodCArgument), methodCId, new HashMap<String, String>()));

        StringBuilder builder = new StringBuilder();
        builder.append(OutgoingMessageType.CALLSTACKUPDATED);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(methodAName);
        builder.append("(" + methodAArgument1 + ", " + methodAArgument2 + ")");
        builder.append(MessageFormatter.ASSIGN_SYMBOL);
        builder.append(methodAId);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(methodBName);
        builder.append("(" + methodBArgument + ")");
        builder.append(MessageFormatter.ASSIGN_SYMBOL);
        builder.append(methodBId);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(methodCName);
        builder.append("(" + methodCArgument + ")");
        builder.append(MessageFormatter.ASSIGN_SYMBOL);
        builder.append(methodCId);
        message = builder.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        CallStackUpdatedMessage updated = new CallStackUpdatedMessage(stack);
        Assert.assertNotNull(updated);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        CallStackUpdatedMessage updated = new CallStackUpdatedMessage(stack);
        Assert.assertEquals(message, updated.toString());
    }
}
