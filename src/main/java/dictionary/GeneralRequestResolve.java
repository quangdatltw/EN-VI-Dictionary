package dictionary;

import dictionary.db.LocalDictionary;
import dictionary.db.LocalDictionaryRequestHandle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * The type General request resolve.
 */
public class GeneralRequestResolve {
    private static LocalDictionaryRequestHandle librarian = new LocalDictionaryRequestHandle();

    /**
     * Get list of words starting with prefix.
     *
     * @param prefix the prefix
     * @return the list
     */
    public static List<String> getSearchedList(String prefix) {

        List<String> wordlist = LocalDictionary.getWordlist();
        List<String> result = new ArrayList<>();

        if (prefix == null || prefix.isEmpty()) {
            return result;
        }
        int begin = 0;
        int end = wordlist.size() - 1;

        try {
            begin = LocalDictionary.getIndex().get((int) prefix.charAt(0) - 97);
            if ((int) prefix.charAt(0) - 97 < 25) {
                end = LocalDictionary.getIndex().get((int) prefix.charAt(0) - 96) - 1;
            }
        } catch (IndexOutOfBoundsException ignored) {
            end = LocalDictionary.getIndex().get(0) - 1;
        }
        wordlist = wordlist.subList(begin, end);

        for (String word : wordlist) {
            if (word.startsWith(prefix)) {
                result.add(word);
            }
        }
        return result;
    }

    /**
     * Input file.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean importDFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1].replace("\\n", "\n");
                    LocalDictionaryRequestHandle.loadWord(key, value);
                }
            }
        } catch (IOException e) {
            return true;
        }
        return false;
    }

    /**
     * Export file.
     *
     * @return the boolean
     */
    public static boolean exportDFile() {
        try (FileWriter writer = new FileWriter("src/main/resources/external_dictionary/Dictionary_EX.txt")) {
            String def;
            for (String word : LocalDictionary.getWordlist()) {
                def = librarian.getDefinition(word).replace("\n", "\\n");
                writer.write(word + "\t" + def + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
