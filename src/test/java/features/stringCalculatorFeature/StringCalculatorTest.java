package features.stringCalculatorFeature;

import junit.framework.TestCase;

public class StringCalculatorTest extends TestCase {

    public void testAddFirstStep() {
        assertEquals("0",StringCalculator.add(""));
        assertEquals("1.0",StringCalculator.add("1"));
        assertEquals(String.valueOf(1.1+2.2),StringCalculator.add("1.1,2.2"));
    }

    public void testManyNumbers() {
        assertEquals("100.0",StringCalculator.add("10,10,10,10,10,50"));
    }

    public void testNewLineSeperator() {
        assertEquals("6.0",StringCalculator.add("1\n2,3"));
        assertEquals("Number expected but '\\n' found at position 6.",StringCalculator.add("175.2,\n35"));
    }
}