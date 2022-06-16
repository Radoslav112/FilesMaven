import datasource.GenerateText;
import models.WordsInFile;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import workers.FileWorker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class FilesMain {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/log4j.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyConfigurator.configure(props);

        Logger logger = Logger.getLogger(FilesMain.class);
        logger.info("Main");

        //create file
        FileWorker fileWorker = new FileWorker("D:\\DEV\\Intelij\\Files\\File.txt");
        if(fileWorker.isExist()){
            System.out.println(String.format("File with name %s already exists.\n",fileWorker.getFileName()));
        }
        else {
            if(fileWorker.createFile()){
                System.out.println("File created successfully\n");
            }
            else{
                System.out.println("Something went wrong\n");
            }
        }

        //add text to file
        String text;
        GenerateText generateText = new GenerateText();
        text = generateText.getText();

        //read from file
        fileWorker.writeToFile(text);
        List<String> words = fileWorker.readFromFile();

        //add words from list to model for easier word
        WordsInFile wordsInFile = new WordsInFile();

        for(String word:words){
            wordsInFile.add(word);
        }

        wordsInFile.sort();//sort words in model
        wordsInFile.print();//print sorted words from model
    }
}
