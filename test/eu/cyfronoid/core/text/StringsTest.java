package eu.cyfronoid.core.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class StringsTest {

    public StringsTest() {
    }

    @Test
    public void implodeArray() {
        String[] inputArray = {"cyfron", "widmo", "test"};
        String glueString = ",";
        String expResult = "cyfron,widmo,test";
        String result = Strings.implode(inputArray, glueString);
        assertEquals(expResult, result);
    }

    @Test
    public void anyContains() {
        String[] inputArray = {"cyfron", "widmo", "test"};
        System.out.println(Arrays.toString(inputArray));
        assertTrue(Strings.anyContains(inputArray, "idm"));
    }
}