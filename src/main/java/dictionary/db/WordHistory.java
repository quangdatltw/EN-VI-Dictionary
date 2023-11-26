package dictionary.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WordHistory {
    private static List<String> history = new ArrayList<>();
    public static List<String> getHistory() {
        List<String> result = new ArrayList<>(history);
        Collections.reverse(result);
        return result;
    }

    public static void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/external_dictionary/history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
               history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addWord(String word) {
        if(word == null || word.isEmpty()) {
            return;
        }
        history.remove(word);
        if (history.size() < 20) {
            history.add(word);
        } else {
            history.remove(0);
            history.add(word);
        }
    }

    public static void exportHistoryToFile() {
        try (FileWriter writer = new FileWriter("src/main/resources/external_dictionary/history.txt")) {
            for (String word : history) {
                writer.write(word + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
