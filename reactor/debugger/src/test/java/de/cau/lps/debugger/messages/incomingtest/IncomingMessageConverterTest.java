package de.cau.lps.debugger.messages.incomingtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.exception.MessageConversionException;
import de.cau.lps.debugger.exception.MessageWronglyFormattedException;
import de.cau.lps.debugger.exception.UnknownMessageTypeException;
import de.cau.lps.debugger.messages.MessageFormatter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageConverter;
import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
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
 * Tests the {@link IncomingMessageConverter} class.
 */
public class IncomingMessageConverterTest {

    private static int line;
    private static int row;
    private static String methodName;
    private static String variableName;
    private static String variableValue;
    private static String variableAddress;

    private static String addBreakpointMessage;
    private static String popFromCallStackMessage;
    private static String pushOntoCallStackMessage;
    private static String removeBreakpointMessage;
    private static String runToNextBreakpointMessage;
    private static String stepMessage;
    private static String stepOverMessage;
    private static String updatePositionMessage;
    private static String updateTableMessage;
    private static String startReplayMessage;
    private static String stopReplayMessage;
    private static String endOfTapeMessage;
    private static String removeAllBreakpointsMessage;
    private static String enableAllBreakpointsMessage;
    private static String disableAllBreakpointsMessage;
    private static String runToEndOfMethodMessage;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        line = 2;
        row = 3;
        methodName = "MyMethod";
        variableName = "myVar";
        variableValue = "123";
        variableAddress = "0x0001";

        StringBuilder builder = new StringBuilder();
        builder.append(IncomingMessageType.ADDBREAKPOINT);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(line);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(row);
        addBreakpointMessage = builder.toString();

        popFromCallStackMessage = IncomingMessageType.POPFROMCALLSTACK.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.PUSHONTOCALLSTACK);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(methodName);
        pushOntoCallStackMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.REMOVEBREAKPOINT);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(line);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(row);
        removeBreakpointMessage = builder.toString();

        runToNextBreakpointMessage = IncomingMessageType.RUNTONEXTBREAKPOINT.toString();
        stepMessage = IncomingMessageType.STEP.toString();
        stepOverMessage = IncomingMessageType.STEPOVER.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.UPDATEPOSITION);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(line);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(row);
        updatePositionMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.UPDATETABLE);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(variableName);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(variableValue);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(variableAddress);
        updateTableMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.STARTREPLAY);
        builder.append(MessageFormatter.DELIMITER);
        builder.append(methodName);
        startReplayMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.STOPREPLAY);
        stopReplayMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.ENDOFTAPE);
        endOfTapeMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.REMOVEALLBREAKPOINTS);
        removeAllBreakpointsMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.ENABLEALLBREAKPOINTS);
        enableAllBreakpointsMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.DISABLEALLBREAKPOINTS);
        disableAllBreakpointsMessage = builder.toString();

        builder = new StringBuilder();
        builder.append(IncomingMessageType.RUNTOENDOFMETHOD);
        runToEndOfMethodMessage = builder.toString();

    }

    /**
     * Tests the conversion to an {@link AddBreakpointMessage}.
     */
    @Test
    public void convertAddBreakpointMessage() {
        try {
            Assert.assertTrue(IncomingMessageConverter.convert(addBreakpointMessage) instanceof AddBreakpointMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link PopFromCallStackMessage}.
     */
    @Test
    public void convertPopFromCallStackMessage() {
        try {
            Assert.assertTrue(
                IncomingMessageConverter.convert(popFromCallStackMessage) instanceof PopFromCallStackMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link PushOntoCallStackMessage}.
     */
    @Test
    public void convertPushOntoCallStackMessage() {
        try {
            Assert.assertTrue(
                IncomingMessageConverter.convert(pushOntoCallStackMessage) instanceof PushOntoCallStackMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link RemoveBreakpointMessage}.
     */
    @Test
    public void convertRemoveBreakpointMessage() {
        try {
            Assert.assertTrue(
                IncomingMessageConverter.convert(removeBreakpointMessage) instanceof RemoveBreakpointMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link RunToNextBreakpointMessage}.
     */
    @Test
    public void convertRunToNextBreakpointMessage() {
        try {
            Assert.assertTrue(
                IncomingMessageConverter.convert(runToNextBreakpointMessage) instanceof RunToNextBreakpointMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link StepMessage}.
     */
    @Test
    public void convertStepMessage() {
        try {
            Assert.assertTrue(IncomingMessageConverter.convert(stepMessage) instanceof StepMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link StepOverMessage}.
     */
    @Test
    public void convertStepOverMessage() {
        try {
            Assert.assertTrue(IncomingMessageConverter.convert(stepOverMessage) instanceof StepOverMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link UpdatePositionMessage}.
     */
    @Test
    public void convertUpdatePositionMessage() {
        try {
            Assert.assertTrue(IncomingMessageConverter.convert(updatePositionMessage) instanceof UpdatePositionMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link UpdateTableMessage}.
     */
    @Test
    public void convertUpdateTableMessage() {
        try {
            Assert.assertTrue(IncomingMessageConverter.convert(updateTableMessage) instanceof UpdateTableMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link StartReplayMessage}.
     */
    @Test
    public void convertStartReplayMessage() {
        try {
            Assert.assertTrue(IncomingMessageConverter.convert(startReplayMessage) instanceof StartReplayMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link StopReplayMessage}.
     */
    @Test
    public void convertStopReplayMessage() {
        try {
            Assert.assertTrue(IncomingMessageConverter.convert(stopReplayMessage) instanceof StopReplayMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link EndOfTapeMessage}.
     */
    @Test
    public void convertEndOfTapeMessage() {
        try {
            Assert.assertTrue(IncomingMessageConverter.convert(endOfTapeMessage) instanceof EndOfTapeMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to {@link RemoveAllBreakpointsMessage}.
     */
    @Test
    public void convertRemoveAllBreakpointsMessage() {
        try {
            Assert.assertTrue(
                IncomingMessageConverter.convert(removeAllBreakpointsMessage) instanceof RemoveAllBreakpointsMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to a {@link EnableAllBreakpointsMessage}.
     */
    @Test
    public void convertEnableAllBreakpointsMessage() {
        try {
            Assert.assertTrue(
                IncomingMessageConverter.convert(enableAllBreakpointsMessage) instanceof EnableAllBreakpointsMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to a {@link DisableAllBreakpointsMessage}.
     */
    @Test
    public void convertDisableAllBreakpointsMessage() {
        try {
            Assert.assertTrue(
                IncomingMessageConverter.convert(disableAllBreakpointsMessage) instanceof DisableAllBreakpointsMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion to a {@link RunToEndOfMethodMessage}.
     */
    @Test
    public void convertRunToEndOfMethodMessage() {
        try {
            Assert.assertTrue(
                IncomingMessageConverter.convert(runToEndOfMethodMessage) instanceof RunToEndOfMethodMessage);
        } catch (MessageConversionException | MessageWronglyFormattedException | UnknownMessageTypeException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * Tests the conversion of an unknown message. It should throw.
     */
    @Test
    public void convertUnknownMessage() {
        try {
            IncomingMessageConverter.convert("BlahBlahBlah");
            Assert.fail();
        } catch (UnknownMessageTypeException | MessageConversionException | MessageWronglyFormattedException e) {
            return;
        }
        Assert.fail();
    }
}
