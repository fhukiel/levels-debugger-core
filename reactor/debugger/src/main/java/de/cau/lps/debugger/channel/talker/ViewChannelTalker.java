package de.cau.lps.debugger.channel.talker;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.common.Call;
import de.cau.lps.debugger.common.Position;
import de.cau.lps.debugger.languagespecific.api.Variable;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.view.AutoSteppingDisabledMessage;
import de.cau.lps.debugger.messages.outgoing.view.AutoSteppingEnabledMessage;
import de.cau.lps.debugger.messages.outgoing.view.CallStackUpdatedMessage;
import de.cau.lps.debugger.messages.outgoing.view.EndOfReplayTapeMessage;
import de.cau.lps.debugger.messages.outgoing.view.PositionUpdatedMessage;
import de.cau.lps.debugger.messages.outgoing.view.ReadyMessage;
import de.cau.lps.debugger.messages.outgoing.view.TableUpdatedMessage;

/**
 * Sends messages to a view {@link CommunicationChannel}.
 * 
 * @author Thomas Ulrich
 */
public class ViewChannelTalker extends AbstractChannelTalker {
    private static Logger logger = LoggerFactory.getLogger(ViewChannelTalker.class);

    /**
     * Initializes a new instance of the {@link ViewChannelTalker} class.
     * 
     * @param channel
     *            A {@link CommunicationChannel} object to send messages to.
     */
    public ViewChannelTalker(CommunicationChannel channel) {
        super(channel);
    }

    /**
     * Sends a {@link Position} object to the view.
     * 
     * @param position
     *            The {@link Position} to send.
     * @throws IOException
     *             Thrown if an exception occurs while sending.
     */
    public void sendPosition(Position position) throws IOException {
        logger.debug("Sending position update to view.");
        this.sendMessage(new PositionUpdatedMessage(position));
    }

    /**
     * Sends a call stack to the view.
     * 
     * @param callStack
     *            A Stack of {@link Call}s.
     * @throws IOException
     *             Thrown if an exception occurs while sending.
     */
    public void sendCallStack(Stack<Call> callStack) throws IOException {
        logger.debug("Sending call stack to view.");
        this.sendMessage(new CallStackUpdatedMessage(callStack));
    }

    /**
     * Sends a variable table to the view.
     * 
     * @param variables
     *            A List of {@link Variable}s representing the variable table.
     * @throws IOException
     *             Thrown if an exception occurs while sending.
     */
    public void sendVariableTable(List<Variable> variables) throws IOException {
        logger.debug("Sending TableUpdated message to view.");
        this.sendMessage(new TableUpdatedMessage(variables));
    }

    /**
     * Sends a 'ready' message to the view.
     * 
     * @throws IOException
     *             Thrown if an exception occurs while sending.
     */
    public void sendReady() throws IOException {
        logger.debug("Sending Ready message to view.");
        this.sendMessage(new ReadyMessage(OutgoingMessageType.READY.toString()));
    }

    /**
     * Sends a 'end of replay tape' message to the view.
     * 
     * @throws IOException
     *             Thrown if an exception occurs while sending.
     */
    public void sendEndOfReplayTape() throws IOException {
        logger.debug("Sending end of replay tape to view.");
        this.sendMessage(new EndOfReplayTapeMessage(OutgoingMessageType.ENDOFREPLAYTAPE.toString()));
    }

    /**
     * Sends a 'auto stepping enabled' message to the view.
     * 
     * @throws IOException
     *             Thrown if an exception occurs while sending.
     */
    public void sendAutoSteppingEnabled() throws IOException {
        logger.debug("Sending auto stepping enabled message to view.");
        this.sendMessage(new AutoSteppingEnabledMessage(OutgoingMessageType.AUTOSTEPPINGENABLED.toString()));
    }

    /**
     * Sends a 'auto stepping disabled' message to the view.
     * 
     * @throws IOException
     *             Thrown if an exception occurs while sending.
     */
    public void sendAutoSteppingDisabled() throws IOException {
        logger.debug("Sending auto stepping disabled message to view.");
        this.sendMessage(new AutoSteppingDisabledMessage(OutgoingMessageType.AUTOSTEPPINGDISABLED.toString()));
    }
}
