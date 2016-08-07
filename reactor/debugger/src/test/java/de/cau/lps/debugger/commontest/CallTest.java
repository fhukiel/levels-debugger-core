package de.cau.lps.debugger.commontest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import de.cau.lps.debugger.common.Call;

/**
 * Tests the {@link Call} class.
 * 
 * @author Thomas Ulrich
 */
public class CallTest {

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        Map<String, String> options = new HashMap<String, String>();
        String definingClassId = "MyClass";
        String defClassKey = "DEFINING_CLASS";
        String definingInstanceId = "MyInstance";
        String defInstanceKey = "DEFINING_INSTANCE";
        options.put(defClassKey, definingClassId);
        options.put(defInstanceKey, definingInstanceId);

        String methodName = "myMethod";
        String callId = "42";
        String argument1 = "arg1";
        String argument2 = "arg2";
        ArrayList<String> arguments = new ArrayList<String>();
        arguments.add(argument1);
        arguments.add(argument2);
        Call call = new Call(methodName, arguments, callId, options);

        Assert.assertNotNull(call);
        Assert.assertEquals(methodName, call.getMethodName());
        Assert.assertEquals(callId, call.getAssociatedPushMessageID());
        Assert.assertEquals(argument1, call.getArguments().get(0));
        Assert.assertEquals(argument2, call.getArguments().get(1));
        Assert.assertEquals(definingClassId, call.getOptions().get(defClassKey));
        Assert.assertEquals(definingInstanceId, call.getOptions().get(defInstanceKey));

    }

    /**
     * Tests the marking mechanism.
     */
    @Test
    public void mark() {
        Call call = new Call("myMethod", Arrays.asList("arg1"), "42", new HashMap<String, String>());
        call.setMarked(true);
        Assert.assertTrue(call.isMarked());

        call.setMarked(false);
        Assert.assertFalse(call.isMarked());
    }
}
