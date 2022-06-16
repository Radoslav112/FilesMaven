package models;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import workers.FileWorker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class WordsInFile {
    private Map<String,Integer> words;
    public WordsInFile(){
        words = new HashMap<>();
    }

    public void add(String word){
        if(words.containsKey(word)){
            words.put(word,words.get(word)+1);
        }else{
            words.put(word,1);
        }
    }

    public Map<String, Integer> getWords() {
        return words;
    }

    public void sort(){
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(words.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        words.clear();
        words = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            words.put(aa.getKey(), aa.getValue());
        }
    }

    public void print(){
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/log4j.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyConfigurator.configure(props);

        Logger logger = Logger.getLogger(WordsInFile.class);
        for (Map.Entry<String, Integer> entry : words.entrySet())
        {
            logger.info(entry.getKey() +"\t\t\t"+entry.getValue());
        }
    }
}

