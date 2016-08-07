package de.cau.lps.debugger.messages.incoming;

import de.cau.lps.debugger.exception.MessageConversionException;
import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.exception.UnknownMessageTypeException;
import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.runtime.EndOfTapeMessage;
import de.cau.lps.debugger.messages.incoming.runtime.PopFromCallStackMessage;
import de.cau.lps.debugger.messages.incoming.runtime.PushOntoCallStackMessage;
import de.cau.lps.debugger.messages.incoming.runtime.UpdatePositionMessage;
import de.cau.lps.debugger.messages.incoming.runtime.UpdateTableMessage;
import de.cau.lps.debugger.messages.incoming.view.AddBreakpointMessage;
import de.cau.lps.debugger.messages.incoming.view.DisableAllBreakpointsMessage;
import de.cau.lps.debugger.messages.incoming.view.EnableAllBreakpointsMessage;
import de.cau.lps.debugger.messages.incoming.view.RemoveAllBreakpointsMessage;
import de.cau.lps.debugger.messages.incoming.view.RemoveBreakpointMessage;
import de.cau.lps.debugger.messages.incoming.view.RunToEndOfMethodMessage;
import de.cau.lps.debugger.messages.incoming.view.RunToNextBreakpointMessage;
import de.cau.lps.debugger.messages.incoming.view.StartReplayMessage;
import de.cau.lps.debugger.messages.incoming.view.StepMessage;
import de.cau.lps.debugger.messages.incoming.view.StepOverMessage;
import de.cau.lps.debugger.messages.incoming.view.StopReplayMessage;

/**
 * Converts a given message string to a Message object.
 * 
 * @author Thomas Ulrich
 *
 */
public abstract class IncomingMessageConverter {

    /**
     * Takes a message string and converts it to a concrete Message object.
     * 
     * @param message
     *            The message to convert.
     * @return An {@link AbstractMessage} object.
     * @throws MessageConversionException
     *             Thrown if String cannot be converted to a message object.
     * @throws MessageWronglyFormattedException
     *             Thrown if String is wrongly formatted.
     * @throws UnknownMessageTypeException
     *             Thrown if passed message is of unknown type.
     */
    public static AbstractMessage convert(String message)
        throws MessageConversionException, MessageWronglyFormattedException, UnknownMessageTypeException {

        String[] splitted = message.split(MessageFormatter.DELIMITER);
        IncomingMessageType type = null;
        try {
            type = Enum.valueOf(IncomingMessageType.class, splitted[0]);
        } catch (Exception e) {
            throw new MessageConversionException(
                "An error ocurred while attempting to convert message '" + splitted[0] + "' to incoming message type.");
        }

        switch (type) {
            case ADDBREAKPOINT:
                return new AddBreakpointMessage(message);
            case POPFROMCALLSTACK:
                return new PopFromCallStackMessage(message);
            case PUSHONTOCALLSTACK:
                return new PushOntoCallStackMessage(message);
            case REMOVEBREAKPOINT:
                return new RemoveBreakpointMessage(message);
            case REMOVEALLBREAKPOINTS:
                return new RemoveAllBreakpointsMessage(message);
            case RUNTONEXTBREAKPOINT:
                return new RunToNextBreakpointMessage(message);
            case STEP:
                return new StepMessage(message);
            case STEPOVER:
                return new StepOverMessage(message);
            case UPDATEPOSITION:
                return new UpdatePositionMessage(message);
            case UPDATETABLE:
                return new UpdateTableMessage(message);
            case STARTREPLAY:
                return new StartReplayMessage(message);
            case STOPREPLAY:
                return new StopReplayMessage(message);
            case ENDOFTAPE:
                return new EndOfTapeMessage(message);
            case DISABLEALLBREAKPOINTS:
                return new DisableAllBreakpointsMessage(message);
            case ENABLEALLBREAKPOINTS:
                return new EnableAllBreakpointsMessage(message);
            case RUNTOENDOFMETHOD:
                return new RunToEndOfMethodMessage(message);
            default:
                throw new UnknownMessageTypeException(type.toString());
        }
    }
}
