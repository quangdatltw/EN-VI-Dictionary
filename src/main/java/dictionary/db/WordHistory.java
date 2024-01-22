package dictionary.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordHistory {
    private static final List<String> history = new ArrayList<>();

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
        String filePath = "history.txt";
        URL jarUrl = WordHistory.class.getProtectionDomain().getCodeSource().getLocation();
        Path jarPath;
        try {
            jarPath = Paths.get(jarUrl.toURI());
            Path parentDir = jarPath.getParent();
            parentDir = parentDir.resolve("res");

            try (InputStream inputStream = Files.newInputStream(parentDir.resolve(filePath));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    history.add(line);
                }
            }
        } catch (IOException | URISyntaxException e) {
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
        exportToFile();
    }

    /**
     * Export word search history to file.
     */
    public static void exportToFile() {
        URL jarUrl = WordHistory.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            Path jarPath = Paths.get(jarUrl.toURI());
            Path parentDir = jarPath.getParent();
            parentDir = parentDir.resolve("res");

            try (FileWriter writer = new FileWriter(parentDir.resolve("history.txt").toFile())) {
                for (String word : history) {
                    writer.write(word + "\n");
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}

