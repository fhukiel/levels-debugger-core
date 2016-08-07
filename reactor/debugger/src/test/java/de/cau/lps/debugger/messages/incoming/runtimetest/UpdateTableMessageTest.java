package de.cau.lps.debugger.messages.incoming.runtimetest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.runtime.UpdateTableMessage;

/**
 * Tests the {@link UpdateTableMessage} class.
 */
public class UpdateTableMessageTest {

    private static String brokenMessage;
    private static String simpleMessage;
    private static String complexMessage;
    private static String variableName;
    private static String variableValue;
    private static String variableAddress;
    private static String optionAKey;
    private static String optionAValue;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {

        variableName = "MyVar";
        variableValue = "642";
        variableAddress = "0x0001";
        optionAKey = "aKey";
        optionAValue = "aValue";

        StringBuilder builder = new StringBuilder();
        builder.append(IncomingMessageType.UPDATETABLE);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(variableName);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(variableValue);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(variableAddress);
        simpleMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.UPDATETABLE);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(variableName);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(variableValue);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(variableAddress);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(optionAKey);
        builder.append(MessageFormatter.ASSIGN_SYMBOL);
        builder.append(optionAValue);
        complexMessage = builder.toString();

        brokenMessage = "xxx;13";
    }

    /**
     * Tests the simple constructor.
     */
    @Test
    public void simpleCtor() {
        UpdateTableMessage table;
        try {
            table = new UpdateTableMessage(simpleMessage);
            Assert.assertNotNull(table);
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the simple constructor.
     */
    @Test
    public void complexCtor() {
        UpdateTableMessage table;
        try {
            table = new UpdateTableMessage(complexMessage);
            Assert.assertNotNull(table);
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the simple string representation getter.
     */
    @Test
    public void getSimpleStringRepresentation() {
        UpdateTableMessage table;
        try {
            table = new UpdateTableMessage(simpleMessage);
            Assert.assertEquals(simpleMessage, table.toString());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the simple string representation getter.
     */
    @Test
    public void getComplexStringRepresentation() {
        UpdateTableMessage table;
        try {
            table = new UpdateTableMessage(complexMessage);
            Assert.assertEquals(complexMessage, table.toString());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the variable name getter.
     */
    @Test
    public void getVarName() {
        UpdateTableMessage table;
        try {
            table = new UpdateTableMessage(simpleMessage);
            Assert.assertEquals(variableName, table.getVariableName());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the variable value getter.
     */
    @Test
    public void getVarValue() {
        UpdateTableMessage table;
        try {
            table = new UpdateTableMessage(simpleMessage);
            Assert.assertEquals(variableValue, table.getVariableValue());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the variable address getter.
     */
    @Test
    public void getVarAddress() {
        UpdateTableMessage table;
        try {
            table = new UpdateTableMessage(simpleMessage);
            Assert.assertEquals(variableAddress, table.getVariableAddress());
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the variable options getter.
     */
    @Test
    public void getOptions() {
        UpdateTableMessage table;
        try {
            table = new UpdateTableMessage(complexMessage);
            Assert.assertEquals(optionAValue, table.getOptions().get(optionAKey));
        } catch (MessageWronglyFormattedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tries to construct an {@link UpdateTableMessage} from a falsely formatted string.
     */
    @SuppressWarnings("unused")
    @Test
    public void tryBrokenMessage() {
        try {
            new UpdateTableMessage(brokenMessage);
            Assert.fail();
        } catch (MessageWronglyFormattedException e) {
            return;
        }

        Assert.fail();
    }
}
