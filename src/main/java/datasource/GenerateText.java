package datasource;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * Class that generate text for tests
 */
public class GenerateText {
    private String text;

    public GenerateText() {
        Random random = new Random();
        text = "";
        for (int i = random.nextInt(500)+500;i>0;i--){
            text+=(GenerateRandomString()+' ');
            if(i%10==0)text+='\n';
        }
    }

    private String GenerateRandomString(){
        Random random = new Random();
        return RandomStringUtils.randomAlphabetic(random.nextInt(7)+1);
    }

    public String getText() {
        return text;
    }
}
