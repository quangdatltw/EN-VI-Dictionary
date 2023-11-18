package main;

import java.util.ArrayList;
import java.util.Collections;
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

    public static void putWord(String word, String definition) {
        if (checkWord(word)) {
            dictionary.put(word, definition);
        }
        int idx = Collections.binarySearch(wordlist, word);
        if (idx < 0) {
            idx = - idx - 1;
        }

        String theOneThatCall = Thread.currentThread().getStackTrace()[2].getClassName();
        if (!theOneThatCall.contains("DictionaryDatabase")) {
            int start = (int) word.charAt(0) - 96;
            for (int i = start; i < 26; i++) {
                index.set(i, index.get(i) + 1);
            }
        }

        wordlist.add(idx, word);
        dictionary.put(word, definition);
    }

    public static void updateWord(String word, String definition) {
        dictionary.put(word, definition);
    }

    public static void removeWord(String word) {
        wordlist.remove(word);
        dictionary.remove(word);
        for (int i = (int) word.charAt(0) - 96; i < 26; i++) {
            index.set(i, index.get(i) - 1);
        }
    }

    public static boolean checkWord(String word) {
        return dictionary.get(word) != null;
    }

    public static String getDefinition(String word) {
        if (dictionary.get(word) != null) {
            return dictionary.get(word);
        }
        return "word not found! \n";
    }
}