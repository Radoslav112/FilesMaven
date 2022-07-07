package features.stringCalculatorFeature;

import junit.framework.TestCase;

public class StringCalculatorTest extends TestCase {

    public void testEmptyString() {

        String result = StringCalculator.add("");

        assertEquals("0",result);
    }

    public void testAdd() {

        String result1 = StringCalculator.add("1");
        String result2 = StringCalculator.add("1.1,2.2");

        assertEquals("1.0",result1);
        assertEquals(String.valueOf(1.1+2.2),result2);
    }

    public void testManyNumbers() {

        String result = StringCalculator.add("10,10,10,10,10,50");

        assertEquals("100.0",result);
    }

    public void testNewLineSeperator() {

        String result1 = StringCalculator.add("1\n2,3");
        String result2 = StringCalculator.add("175.2,\n35");

        assertEquals("6.0",result1);
        assertEquals("Number expected but '\\n' found at position 6.",result2);
    }


    public void testMissingNumberInLastPosition() {

        String result = StringCalculator.add("1,3,");

        assertEquals("Number expected but EOF found.",result);
    }


    public void testCustomSeparator() {

        String result1 = StringCalculator.add("//;\\n1;2");
        String result2 = StringCalculator.add("//|\\n1|2|3");
        String result3 = StringCalculator.add("//sep\\n2sep3");
        String result4 = StringCalculator.add("//|\\n1|2,3");

        assertEquals("3.0",result1);
        assertEquals("6.0",result2);
        assertEquals("5.0",result3);
        assertEquals("'|' expected but ',' found at position 3.",result4);
    }

    public void testNegativeNumbers() {

        String result1 = StringCalculator.add("-1,2");
        String result2 = StringCalculator.add("2,-4,-5");

        assertEquals("Negative not allowed : -1",result1);
        assertEquals("Negative not allowed : -4, -5",result2);
    }

    public void testMultipleErrors() {

        String result1 = StringCalculator.add("-1,,2");
        String result2 = StringCalculator.add("-1,,-2");

        assertEquals("Negative not allowed : -1\nNumber expected but ',' found at position 3.",result1);
        assertEquals("Negative not allowed : -1\nNumber expected but ',' found at position 3.\nNegative not allowed : -2",result2);
    }
}