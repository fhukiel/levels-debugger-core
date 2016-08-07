package de.cau.lps.debugger.messages.outgoing.viewtest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.commonMocks.VariableTableMock;
import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.languagespecific.api.VariableTable;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.view.TableUpdatedMessage;

/**
 * Tests the {@link TableUpdatedMessage} class.
 */
public class TableUpdatedMessageTest {

    private static ScopeIdentifier varAScope = new ScopeIdentifier("aScope");
    private static String varAName = "VariableA";
    private static String varAValue = "ValueA";
    private static String varAAddress = "0x0001";

    private static ScopeIdentifier varBScope = new ScopeIdentifier("bScope");
    private static String varBName = "VariableB";
    private static String varBValue = "ValueB";
    private static String varBAddress = "0x0000";

    private static String message;
    private static VariableTable table;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        table = new VariableTableMock();
        Map<String, String> aOptions = new HashMap<String, String>();
        Map<String, String> bOptions = new HashMap<String, String>();

        Variable varA = new Variable(varAName, varAValue, varAAddress, aOptions);
        Variable varB = new Variable(varBName, varBValue, varBAddress, bOptions);
        table.update(varAScope, varA);
        table.update(varBScope, varB);

        StringBuilder builder = new StringBuilder();
        builder.append(OutgoingMessageType.TABLEUPDATED);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(
            varAName + MessageFormatter.ASSIGN_SYMBOL + varAValue + MessageFormatter.ASSIGN_SYMBOL + varAAddress);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(
            varBName + MessageFormatter.ASSIGN_SYMBOL + varBValue + MessageFormatter.ASSIGN_SYMBOL + varBAddress);
        message = builder.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        TableUpdatedMessage updated = new TableUpdatedMessage(table.getAll());
        Assert.assertNotNull(updated);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        TableUpdatedMessage updated = new TableUpdatedMessage(table.getAll());
        Assert.assertEquals(message, updated.toString());
    }
}
