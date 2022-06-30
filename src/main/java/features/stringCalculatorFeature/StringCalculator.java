package features.stringCalculatorFeature;

public class StringCalculator {
    public static String add(String number){
        if(number.isEmpty()) {
            return "0";
        }else if(isLastNumberEmpty(number)) {
            return "Number expected but EOF found.";
        }else if(isNumberMissed(number)==1) {
            return String.format("Number expected but '\\n' found at position %d.",number.indexOf(",\n")+1);
        }else if(isNumberMissed(number)==2) {
            return String.format("Number expected but ',' found at position %d.",number.indexOf("\n,")+1);
        }else{
            double res=0;
            String regex = getRegex(number);
            String arr[] = number.split(regex);
            for(String num : arr){
                if(!isNumeric(number)) { // have to check if number contains only defined seperators else err
                    return String.format("'%s' expected but '%s' found at position %d.");
                } else if(isNumberNegarive(num)){
                        return String.format("Negative not allowed : %f",Double.parseDouble(num));
                    }
                res += Double.parseDouble(num);
                }
            return String.valueOf(res);
            }
        }


    private static int isNumberMissed(String number){
        if(number.contains(",\n")){
            return 1;
        } else if(number.contains("\n,")){
            return 2;
        }

        return 0;
    }

    private static boolean isLastNumberEmpty(String number){
        return number.substring(number.length() - 1).equals(",") || number.substring(number.length() - 1).equals("\n");
    }

    private static String getCustomSeparator(String number){
        int endOfSep = number.indexOf("\\n");//will return first occurred '\n'
        int begOfSep = number.indexOf("//")+2;
        return number.substring(begOfSep,endOfSep);
    }

    private static boolean isNumberNegarive(String number){
        return Double.parseDouble(number)<0;
    }

    private static String getRegex(String number){
        String regex = ",|\n";
        if(number.contains("//")){
            String sep = getCustomSeparator(number);
            if(sep.equals("|")){
                regex=getCustomSeparator(number);
            }else{
                regex+=getCustomSeparator(number);
            }
            number = number.substring( number.indexOf("\\n")+2);//delimiter will be removed from number
        }

        return regex;
    }

    private static boolean isNumeric(String number){
        if (number == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(number);
        } catch (NumberFormatException exception) { // if there is exception then it means the String is not numeric
            return false;
        }
        return true;
    }
}
