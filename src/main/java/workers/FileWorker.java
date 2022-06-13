package workers;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.util.*;

public class FileWorker {
    private String fileName;

    public FileWorker(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Create new file with name fileName
     * @return Returns true if file is created. Return false if error occurred while creating file;
     */
    public boolean createFile() {
        try {
            File file = new File(fileName);
            return file.createNewFile();
        }catch (IOException e) {
            Properties props = new Properties();
            try {
                props.load(new FileInputStream("src/main/resources/log4j.properties"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            PropertyConfigurator.configure(props);

            Logger logger = Logger.getLogger(FileWorker.class);
            logger.info("Create file exception: " + e.getMessage() + "\nStackTrace: " + Arrays.toString(e.getStackTrace()));
        }

        return false;
    }

    /**
     * Check if file already exists
     * @return true if file exist else return false
     */
    public boolean isExist(){
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * Write text to file
     * @param text text to be written in file
     */
    public void WriteToFile(String text) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/log4j.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyConfigurator.configure(props);

        Logger logger = Logger.getLogger(FileWorker.class);
        try {
            File file = new File(fileName);
            if(file.setWritable(true)){
                logger.info("Grant permission for write operations on file.\n");
                FileWriter writer = new FileWriter(fileName);
                writer.write(text);
                writer.close();
            }
            else {
                logger.info("Failed to grant permission for write operations on file.\n");
            }

            if(file.setWritable(false)){
                logger.info("Deny permission for write operations on file.\n");
            }
            else {
                logger.info("Failed to deny permission for write operations on file.\n");
            }
        }catch (IOException e) {
            logger.info("Write to file failed: " + e.getMessage() + "\nStackTrace: " + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Read from file
     * @return List of words read from file
     * @throws FileNotFoundException
     */
    public List<String> ReadFromFile() {
        List<String> words = new ArrayList<>();
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/log4j.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyConfigurator.configure(props);

        Logger logger = Logger.getLogger(FileWorker.class);
        try{
            File file = new File(fileName);
            if(file.setReadable(true)){
                //logger.info("Grant permission for read from file.\n");

                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()){
                    for(String word : scanner.nextLine().trim().split("\\,|\\s+")){
                        words.add(word);
                    }
                }
                scanner.close();
            }
            else {
                logger.info("Failed to grant permission for read from file.\n");
            }

            if(file.setReadable(false)){
                logger.info("Deny permission for read from file.\n");
            }else{
                logger.info("Failed to deny permission for read from file.\n");
            }
        } catch (FileNotFoundException e){
            logger.info("Read from file failed: " + e.getMessage() + "\nStackTrace: " + Arrays.toString(e.getStackTrace()));

        }

        return words;
    }

    /**
     * file name getter
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }
}
