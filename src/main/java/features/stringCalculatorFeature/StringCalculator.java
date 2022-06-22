package features.stringCalculatorFeature;

public class StringCalculator {
    public static String add(String number){
        if(number.isEmpty()) {
            return "0";
        }else {
            double res=0;
            String arr[] = number.split(",|\n");
            for(String num : arr){
                res += Double.parseDouble(num);
            }
            return String.valueOf(res);
        }

    }
}
