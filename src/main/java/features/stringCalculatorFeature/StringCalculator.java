package features.stringCalculatorFeature;

import features.stringCalculatorFeature.helpers.Error;
import features.stringCalculatorFeature.helpers.Pair;

import java.util.ArrayList;
import java.util.List;

import static features.stringCalculatorFeature.helpers.Error.*;

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

        String regex = getRegex(number);
        String sep = null;
        double res=0;
        String errorMessage = "";
        if(number.contains("//")){
            sep = getCustomSeparator(number);
            number = number.substring( number.indexOf("\\n")+2);//delimiter will be removed from number
        }

        List<String> arr = List.of(number.split(regex));
        List<Pair<Error,String>> errors = new ArrayList<>();
        for(String num : arr){
            Error  err = checkForErrors(number,num);
            if(err==NO_ERROR){
                res += Double.parseDouble(num);
            } else {
                errors.add(new Pair<>(err,num));
            }
        }

        if(errors.isEmpty()){
            return String.valueOf(res);
        }

        errorMessage = setErrorMsg(errors,number,sep).toString();

        return errorMessage;
    }

    private static Error checkForErrors(String number, String num) {
        if(isNegative(num)){
            return NEGATIVE_NOT_ALLOWED;
        } else if(isNumberMissed(num, number)==MISSED_NUMBER_END_LINE_FOUND) {
            return MISSED_NUMBER_END_LINE_FOUND;
        } else if(isNumberMissed(num, number)==MISSED_NUMBER_COMMA_FOUND) {
            return MISSED_NUMBER_COMMA_FOUND;
        } else if(!isNumeric(num)) {
            return UNEXPECTED_CHAR;
        } else if(isLastNumberEmpty(number)) {
            return  UNEXPECTED_EOF;
        }  else {
            return NO_ERROR;
        }
    }

    private static int getIndexOfUnexpectedNewLine(String number) {
        if(number.contains(",\n")){
            return number.indexOf(",\n")+1;
        }
        return number.indexOf("\n\n")+1;
    }

    private static int getIndexOfUnexpectedComma(String number) {
        if(number.contains("\n,")){
            return number.indexOf("\n,")+1;
        }
        return number.indexOf(",,")+1;
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
    private static Error isNumberMissed(String num, String number){
        if(num.isEmpty()){
            if(number.contains(",\n")||number.contains("\n\n")){
                return MISSED_NUMBER_END_LINE_FOUND;
            } else if(number.contains("\n,")||number.contains(",,")){
                return MISSED_NUMBER_COMMA_FOUND;
            }
        }

        return NO_ERROR;
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

    private static StringBuilder setErrorMsg(List<Pair<Error,String>> errors,String number,String sep){
        Error lastError = null;
        StringBuilder errorMsg = new StringBuilder();
        boolean isUnexpectedEOFAdded = false; // if unexpected end of file error is added to error message the flag will be true and it will not add to the message again
        for(Pair<Error,String> pair : errors){

            switch (pair.getL()){
                case UNEXPECTED_EOF:
                    if(!isUnexpectedEOFAdded){
                        errorMsg = setMsg(errorMsg, new StringBuilder("Number expected but EOF found."),true);
                        isUnexpectedEOFAdded = true;
                    }

                    lastError=UNEXPECTED_EOF;
                    break;
                case NEGATIVE_NOT_ALLOWED:
                    if(lastError==NEGATIVE_NOT_ALLOWED){
                        errorMsg = setMsg(errorMsg, new StringBuilder(", ").append(pair.getR()) ,false);
                    } else {
                        errorMsg = setMsg(errorMsg, new StringBuilder("Negative not allowed : ").append(pair.getR()) ,true);
                    }

                    lastError=NEGATIVE_NOT_ALLOWED;
                    break;
                case MISSED_NUMBER_END_LINE_FOUND:
                    errorMsg = setMsg(errorMsg, new StringBuilder("Number expected but '\\n' found at position ").append(getIndexOfUnexpectedNewLine(number)) ,true).append(".");

                    lastError=MISSED_NUMBER_END_LINE_FOUND;
                    break;
                case UNEXPECTED_CHAR:

                    String unexpectedChar = findUnexpectedChar(pair.getR());
                    int pos = number.indexOf(unexpectedChar);

                    errorMsg = setMsg(errorMsg, new StringBuilder("'").append(sep).append("' expected but '").append(unexpectedChar).append("' found at position ").append(pos).append(".") ,true);


                    lastError=UNEXPECTED_CHAR;
                    break;
                case MISSED_NUMBER_COMMA_FOUND:
                    errorMsg = setMsg(errorMsg, new StringBuilder("Number expected but ',' found at position ").append(getIndexOfUnexpectedComma(number)).append("."),true);

                    lastError=MISSED_NUMBER_COMMA_FOUND;
                    break;
                case MISSED_NUMBER_CUSTOM_SEP_FOUND:
                    errorMsg = setMsg(errorMsg, new StringBuilder("Number expected but '").append(sep).append("' found at position ").append(getIndexOfUnexpectedCustomSep(number,sep)).append("."),true);

                    lastError=MISSED_NUMBER_CUSTOM_SEP_FOUND;
                    break;
            }
        }

        return errorMsg;
    }

    private static int getIndexOfUnexpectedCustomSep(String number, String sep) {
        return number.indexOf(sep+sep)+sep.length()+1;
    }


    private static StringBuilder setMsg(StringBuilder errors, StringBuilder message, boolean newLine){

        if(errors.isEmpty()) {
            errors = errors.append(message);
        } else if(newLine) {
            errors = errors.append("\n").append(message) ;
        } else {
            errors = errors.append(message);
        }

        return errors;
    }
}

