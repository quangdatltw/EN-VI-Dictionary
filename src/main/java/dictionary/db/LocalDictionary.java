package dictionary.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class LocalDictionary {
    protected static HashMap<String, String> dictionary = new HashMap<>();
    protected static ArrayList<String> wordlist = new ArrayList<>();
    protected static ArrayList<Integer> index = new ArrayList<>(Collections.nCopies(26, 0));
    protected static DatabaseRequestHandle database = new DatabaseRequestHandle();

    /**
     * Gets wordlist.
     *
     * @return the wordlist
     */
    public static ArrayList<String> getWordlist() {
        return wordlist;
    }

    /**
     * Gets index (Position of the start of each prefix char in wordlist)
     *
     * @return the index
     */
    public static ArrayList<Integer> getIndex() {
        return index;
    }
}
