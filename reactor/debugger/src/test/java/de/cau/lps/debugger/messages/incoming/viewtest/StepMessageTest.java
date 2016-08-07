package de.cau.lps.debugger.messages.incoming.viewtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.view.StepMessage;

/**
 * Tests the {@link StepMessage} class.
 */
public class StepMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = IncomingMessageType.STEP.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        StepMessage step = new StepMessage(message);
        Assert.assertNotNull(step);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        StepMessage step = new StepMessage(message);
        Assert.assertEquals(message, step.toString());
    }
}
