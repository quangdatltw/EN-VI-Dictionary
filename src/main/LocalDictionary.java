package main;

import java.util.ArrayList;
import java.util.HashMap;

public class LocalDictionary {

    private static HashMap<String, String> dictionary = new HashMap<>();
    private static ArrayList<String> wordlist = new ArrayList<>();

    private static ArrayList<Integer> index = new ArrayList<>();


    public static ArrayList<String> getWordlist() {
        return wordlist;
    }

    public static ArrayList<Integer> getIndex() {
        return index;
    }

    public static void toWordList(String word) {
        wordlist.add(word);
    }

    public static void putWord(String word, String definition) {
        dictionary.put(word.toLowerCase(), definition);
    }

    public static void removeWord(String word) {
        wordlist.remove(word);
        dictionary.remove(word);
        for (int i = (int) word.charAt(0) - 96; i < 26; i++) {
            index.set(i, index.get(i) - 1);
        }
    }

    public static String getDefinition(String word) {
        if (dictionary.get(word.toLowerCase()) != null) {
            return dictionary.get(word.toLowerCase());
        }
        return "word not found! \n";
    }
}