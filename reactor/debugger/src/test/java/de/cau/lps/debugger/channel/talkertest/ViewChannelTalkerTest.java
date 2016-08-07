package de.cau.lps.debugger.channel.talkertest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Test;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.channel.CommunicationChannelTarget;
import de.cau.lps.debugger.channel.provider.AlwaysThrowingChannelMock;
import de.cau.lps.debugger.channel.provider.CommunicationChannelMock;
import de.cau.lps.debugger.channel.provider.CommunicationChannelProvider;
import de.cau.lps.debugger.channel.talker.ViewChannelTalker;
import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.commonMocks.VariableTableMock;
import de.cau.lps.debugger.languagespecific.api.ScopeIdentifier;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.languagespecific.api.VariableTable;
import de.cau.lps.debugger.messages.outgoing.view.CallStackUpdatedMessage;
import de.cau.lps.debugger.messages.outgoing.view.PositionUpdatedMessage;
import de.cau.lps.debugger.messages.outgoing.view.TableUpdatedMessage;

/**
 * Tests the {@link ViewChannelTalker} class.
 * 
 * @author Thomas Ulrich
 */
public class ViewChannelTalkerTest {

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        Assert.assertNotNull(new ViewChannelTalker(CommunicationChannelProvider.getViewChannel(true)));
    }

    /**
     * Tests the sendPosition method.
     */
    @Test
    public void sendPosition() {
        Position pos = new Position(1, 2);
        try (CommunicationChannelMock mockChannel = (CommunicationChannelMock) CommunicationChannelProvider
            .getViewChannel(true)) {
            ViewChannelTalker talker = new ViewChannelTalker(mockChannel);
            talker.sendPosition(pos);
            PositionUpdatedMessage sent = (PositionUpdatedMessage) mockChannel.getSentMessages().get(0);
            Assert.assertEquals(sent.toString(), new PositionUpdatedMessage(pos).toString());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the sendCallStack method.
     */
    @Test
    public void sendCallStack() {
        Call call = new Call("myMethod", Arrays.asList("arg1"), "1", new HashMap<String, String>());
        Stack<Call> callStack = new Stack<Call>();
        callStack.push(call);
        try (CommunicationChannelMock mockChannel = (CommunicationChannelMock) CommunicationChannelProvider
            .getViewChannel(true)) {
            ViewChannelTalker talker = new ViewChannelTalker(mockChannel);
            talker.sendCallStack(callStack);
            CallStackUpdatedMessage sent = (CallStackUpdatedMessage) mockChannel.getSentMessages().get(0);
            Assert.assertEquals(sent.toString(), new CallStackUpdatedMessage(callStack).toString());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the sendVariableTable method.
     */
    @Test
    public void sendVariableTable() {
        ScopeIdentifier localScope = new ScopeIdentifier("scopeId");
        VariableTable table = new VariableTableMock();
        Variable variable = new Variable("VarName", "VarValue", "VarAddress", new HashMap<String, String>());
        table.update(localScope, variable);
        try (CommunicationChannelMock mockChannel = (CommunicationChannelMock) CommunicationChannelProvider
            .getViewChannel(true)) {
            ViewChannelTalker talker = new ViewChannelTalker(mockChannel);
            talker.sendVariableTable(table.getVisibleInScope(localScope));
            TableUpdatedMessage sent = (TableUpdatedMessage) mockChannel.getSentMessages().get(0);
            Assert.assertEquals(sent.toString(),
                new TableUpdatedMessage(table.getVisibleInScope(localScope)).toString());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the recovery mechanism when a message cannot be sent.
     */
    @Test
    public void failToSend() {
        boolean hasThrown = false;
        try (CommunicationChannel mockChannel = new AlwaysThrowingChannelMock(CommunicationChannelTarget.VIEW)) {
            ViewChannelTalker talker = new ViewChannelTalker(mockChannel);
            talker.sendPosition(new Position());
        } catch (Exception e) {
            hasThrown = true;
            Assert.assertTrue(true);
        }
        Assert.assertTrue(hasThrown);
    }
}
