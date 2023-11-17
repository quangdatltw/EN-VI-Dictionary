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
        if (dictionary.get(word) != null) {
            dictionary.put(word.toLowerCase(), definition);
            return;
        }
        String theOneThatCall;
        int idx = Collections.binarySearch(wordlist, word);
        if (idx < 0) {
            idx = - idx - 1;
        }
        theOneThatCall = Thread.currentThread().getStackTrace()[2].getClassName();
        if (!theOneThatCall.substring(theOneThatCall.indexOf(".") + 1).equals("DictionaryDatabase")) {
            int start = (int) word.charAt(0) - 96;
            if (start <= 0) {
                start = 0;
            }
            for (int i = start; i < 26; i++) {
                index.set(i, index.get(i) + 1);
            }
        }
        wordlist.add(idx, word);
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