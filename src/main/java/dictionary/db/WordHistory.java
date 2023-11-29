package dictionary.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordHistory {
    private static List<String> history = new ArrayList<>();

    /**
     * Get the word search history in the correct order.
     *
     * @return the history
     */
    public static ObservableList<String> getHistory() {
        List<String> result = new ArrayList<>(history);
        Collections.reverse(result);
        return FXCollections.observableArrayList(result);
    }

    /**
     * Load word search history from file.
     */
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

    /**
     * Add word to word search history.
     *
     * @param word the word
     */
    public static void addWord(String word) {
        if(word == null || word.isEmpty() || !(new LocalDictionaryRequestHandle().checkWordExistence(word))) {
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

    /**
     * Export word search history to file.
     */
    public static void exportToFile() {
        try (FileWriter writer = new FileWriter("src/main/resources/external_dictionary/history.txt")) {
            for (String word : history) {
                writer.write(word + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

