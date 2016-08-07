package de.cau.lps.debugger.messages.incoming.runtimetest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cau.lps.debugger.messages.incoming.IncomingMessageType;
import de.cau.lps.debugger.messages.incoming.runtime.PopFromCallStackMessage;

/**
 * Tests the {@link PopFromCallStackMessage} class.
 */
public class PopFromCallStackMessageTest {

    private static String message;

    /**
     * Sets up required objects.
     */
    @Before
    public void initialize() {
        message = IncomingMessageType.POPFROMCALLSTACK.toString();
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        PopFromCallStackMessage pop = new PopFromCallStackMessage(message);
        Assert.assertNotNull(pop);
    }

    /**
     * Tests the string representation getter.
     */
    @Test
    public void getStringRepresentation() {
        PopFromCallStackMessage pop = new PopFromCallStackMessage(message);
        Assert.assertEquals(message, pop.toString());
    }

}
