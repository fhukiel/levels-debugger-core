package de.cau.lps.debugger.messages.incoming.runtimetest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.runtime.PushOntoCallStackMessage;

/**
 * Tests the {@link PushOntoCallStackMessage} class.
 */
public class PushOntoCallStackMessageTest {
    private static String methodName;
    private static String optionAKey;
    private static String optionAValue;
    private static String optionBKey;
    private static String optionBValue;
    private static String noArgumentsMethodMessage;
    private static String twoArgumentsMethodMessage;
    private static String argumentA;
    private static String argumentB;
    private static String brokenMessage;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        optionAKey = "optionA";
        optionAValue = "optionAValue";
        optionBKey = "optionB";
        optionBValue = "optionBValue";
        methodName = "myMethod";
        argumentA = "argA";
        argumentB = "argB";

        StringBuilder builder = new StringBuilder();
        builder.append(IncomingMessageType.PUSHONTOCALLSTACK);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(methodName);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(optionAKey);
        builder.append(MessageFormatter.ASSIGN_SYMBOL);
        builder.append(optionAValue);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(optionBKey);
        builder.append(MessageFormatter.ASSIGN_SYMBOL);
        builder.append(optionBValue);
        noArgumentsMethodMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.PUSHONTOCALLSTACK);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(methodName);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(argumentA);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(argumentB);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(optionAKey);
        builder.append(MessageFormatter.ASSIGN_SYMBOL);
        builder.append(optionAValue);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(optionBKey);
        builder.append(MessageFormatter.ASSIGN_SYMBOL);
        builder.append(optionBValue);

        twoArgumentsMethodMessage = builder.toString();

        brokenMessage = "xxxx";
    }

    /**
     * Tests the simple constructor.
     */
    @Test
    public void simpleCtor() {
        PushOntoCallStackMessage push;
        try {
            push = new PushOntoCallStackMessage(noArgumentsMethodMessage);
            Assert.assertNotNull(push);
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the constructor with a method that has arguments.
     */
    @Test
    public void ctor2() {
        PushOntoCallStackMessage push;
        try {
            push = new PushOntoCallStackMessage(twoArgumentsMethodMessage);
            Assert.assertNotNull(push);
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the method name getter.
     */
    @Test
    public void getMethodName() {
        PushOntoCallStackMessage push;
        try {
            push = new PushOntoCallStackMessage(noArgumentsMethodMessage);
            Assert.assertEquals(methodName, push.getMethodName());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the getArguments method.
     */
    @Test
    public void getArguments() {
        PushOntoCallStackMessage push;
        try {
            push = new PushOntoCallStackMessage(twoArgumentsMethodMessage);
            Assert.assertEquals(argumentA, push.getArguments().get(0));
            Assert.assertEquals(argumentB, push.getArguments().get(1));
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        PushOntoCallStackMessage push;
        try {
            push = new PushOntoCallStackMessage(noArgumentsMethodMessage);
            Assert.assertEquals(noArgumentsMethodMessage, push.toString());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation2() {
        PushOntoCallStackMessage push;
        try {
            push = new PushOntoCallStackMessage(twoArgumentsMethodMessage);
            Assert.assertEquals(twoArgumentsMethodMessage, push.toString());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tries to create a {@link PushOntoCallStackMessage} from a falsely formatted string.
     */
    @SuppressWarnings("unused")
    @Test
    public void tryBrokenMessage() {
        try {
            new PushOntoCallStackMessage(brokenMessage);
            Assert.fail();
        } catch (MessageWronglyFormattedException e) {
            return;
        }
        Assert.fail();
    }
}
