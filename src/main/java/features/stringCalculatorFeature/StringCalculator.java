package features.stringCalculatorFeature;

import static features.stringCalculatorFeature.helpers.Constants.*;

public class StringCalculator {

    /**
     * Returns sum of numbers listed in formatted string
     * @param number Formatted string filled with numbers, may contain custom separator
     * @return Return sum of numbers
     */
    public static String add(String number){
        if(number.isEmpty()) {
            return "0";
        }
        if(isLastNumberEmpty(number)) {
            return "Number expected but EOF found.";
        }
        if(isNumberMissed(number)==MISSED_NUMBER_END_LINE_FOUND) {
            return String.format("Number expected but '\\n' found at position %d.",number.indexOf(",\n")+1);
        }
        if(isNumberMissed(number)==MISSED_NUMBER_COMMA_FOUND) {
            return String.format("Number expected but ',' found at position %d.",number.indexOf("\n,")+1);
        }

        double res=0;
        String regex = getRegex(number);
        String sep = null;
        if(number.contains("//")){
            sep = getCustomSeparator(number);
            number = number.substring( number.indexOf("\\n")+2);//delimiter will be removed from number
        }

        String arr[] = number.split(regex);
        for(String num : arr){
            if(isNegative(num)){
                return "Negative not allowed : " + getAllNegatives(arr);
            }
            if(!isNumeric(num)) { // have to check if number contains only defined seperators else err
                String unexpectedChar = findUnexpectedChar(num);
                int pos = findUnexpectedCharPosition(number, unexpectedChar);
                return String.format("'%s' expected but '%s' found at position %d.",sep,unexpectedChar,pos);
            }
            res += Double.parseDouble(num);
        }
        return String.valueOf(res);
    }

    private static String getAllNegatives(String[] numbers) {
        String res = "";
        for (String num:numbers){
            if(isNegative(num)&&res.equals("")){
                res += num;
            }
            else if(isNegative(num)){
                res += ", ".concat(num);
            }
        }

        return res;
    }

    /**
     * Return position of unexpected character in formatted string filled with numbers
     * @param number Formatted string filled with numbers, may contain custom separator
     * @param unexpectedChar String representing the unexpected character
     * @return Return position of unexpected character
     */
    private static int findUnexpectedCharPosition(String number, String unexpectedChar) {
        return number.indexOf(unexpectedChar);
    }

    /**
     * Returns unexpected character in formatted string filled with numbers
     * @param number Formatted string filled with numbers, may contain custom separator
     * @return String representing the unexpected character
     */
    private static String findUnexpectedChar(String number) {
        String arr[] = number.split("");
        for(String s: arr){
            if(!isNumeric(s)){
                return s;
            }
        }
        return null;
    }

    /**
     * Returns code if number is missed
     * @param number Formatted string filled with numbers, may contain custom separator
     * @return Code representing is end of line detected or comma detected inste
     */
    private static int isNumberMissed(String number){
        if(number.contains(",\n")){
            return MISSED_NUMBER_END_LINE_FOUND;
        } else if(number.contains("\n,")){
            return MISSED_NUMBER_COMMA_FOUND;
        }

        return NO_MISSED_NUMBER;
    }

    /**
     * Returns true of last number is missed
     * @param number Formatted string filled with numbers, may contain custom separator
     * @return True if last number is missed or false if it is not missing
     */
    private static boolean isLastNumberEmpty(String number){
        return number.substring(number.length() - 1).equals(",") || number.substring(number.length() - 1).equals("\n");
    }

    /**
     * Return string that contains custom separator
     * @param number Formatted string filled with numbers, may contain custom separator
     * @return String that contains custom separator
     */
    private static String getCustomSeparator(String number){
        int endOfSep = number.indexOf("\\n");//will return first occurred '\n'
        int begOfSep = number.indexOf("//")+2;
        return number.substring(begOfSep,endOfSep);
    }

    /**
     * Return regular expression that contains separators, if given numbers contain
     * custom separator then definition of separator is removed from string
     * @param number Formatted string filled with numbers, may contain custom separator
     * @return Return regular expression
     */
    private static String getRegex(String number){
        String regex = ",|\n";
        if(number.contains("//")){
            String sep = getCustomSeparator(number);
            if(
                    sep.equals(".")||sep.equals("+")||sep.equals("*")||sep.equals("?")||
                    sep.equals("^")||sep.equals("$")||sep.equals("(")||sep.equals(")")||
                    sep.equals("{")||sep.equals("}")||sep.equals("[")||sep.equals("]")||
                    sep.equals("|")||sep.equals("\\")
            ){
                regex = "\\"+sep;
            } else{
                regex=getCustomSeparator(number);
            }
        }

        return regex;
    }

    /**
     * Return true of given variable is numeric else return false
     * @param number String variable to be checked if it is numeric
     * @return Return true if variable is numeric else return false
     */
    private static boolean isNumeric(String number){
        return number.matches("\\d+?\\.?\\d*");
    }

    private static boolean isNegative(String number){
        return number.startsWith("-");
    }
}
