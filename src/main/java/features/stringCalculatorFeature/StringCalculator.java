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
            String arr[] = number.split(",|\n");
            for(String num : arr){
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
}
