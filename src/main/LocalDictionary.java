package main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Local dictionary.
 */
public class LocalDictionary {

    private static HashMap<String, String> dictionary = new HashMap<>();
    public static ArrayList<String> wordlist = new ArrayList<>();

    /**
     * Thêm từ vào trong thư viện
     *
     * @param word là từ cần thêm
     */
    public static void toWordList(String word) {
            wordlist.add(word);
        }

    /**
     * Add word.
     *
     * @param word       the word
     * @param definition the definition
     */
    public static void addWord(String word, String definition) {
            dictionary.put(word.toLowerCase(), definition);
        }

    /**
     * Gets definition.
     *
     * @param word the word
     * @return the definition
     */
    public static String getDefinition(String word) {
            return dictionary.get(word.toLowerCase());
        }
    }
