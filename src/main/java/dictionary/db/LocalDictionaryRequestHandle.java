package dictionary.db;

import java.util.Collections;

public class LocalDictionaryRequestHandle extends LocalDictionary implements Dictionary {


    /**
     * Add word to Local Dictionary and Database.
     *
     * @param word       the word
     * @param definition the definition
     */
    @Override
    public void addWord(String word, String definition) {
        updateIndex(word);
        int idx = findIndex(word);

        wordlist.add(idx, word);
        dictionary.put(word, definition);
        database.addWord(word, definition);
    }

    /**
     * Load words from Database to LocalDictionary
     *
     * @param word       the word
     * @param definition the definition
     */
    public static void loadWord(String word, String definition) {
        updateIndex(word);
        int idx = findIndex(word);

        wordlist.add(idx, word);
        dictionary.put(word, definition);
    }

    /**
     * Update word in both Local and Database dictionary.
     *
     * @param word       the word
     * @param definition the definition
     */
    @Override
    public void updateWord(String word, String definition) {
        dictionary.put(word, definition);
        database.updateWord(word, definition);
    }

    /**
     * Remove word from both Local and Database dictionary.
     *
     * @param word the word
     */
    @Override
    public void removeWord(String word) {
        wordlist.remove(word);
        dictionary.remove(word);
        database.removeWord(word);
        for (int i = (int) word.charAt(0) - 96; i < 26; i++) {
            index.set(i, index.get(i) - 1);
        }
    }

    /**
     * Gets definition of word.
     *
     * @param word the word
     * @return the definition
     */
    public String getDefinition(String word) {
        if (checkWordExistence(word)) {
            return dictionary.get(word);
        }
        return "word not found! \n";
    }

    /**
     * Check word existence.
     *
     * @param word the word
     * @return the boolean
     */
    public boolean checkWordExistence(String word) {
        return dictionary.get(word) != null;
    }

    /**
     * Find word's position in word list.
     *
     */
    private static int findIndex(String word) {
        int idx = Collections.binarySearch(wordlist, word);
        if (idx < 0) {
            idx = - idx - 1;
        }
        if (idx > wordlist.size()) {
            idx = 0;
        }
        return idx;
    }

    /**
     * Update Index.
     *
     */
    private static void updateIndex(String word) {
        int start = (int) word.charAt(0) - 96;
        if (start < 0) {
            start = 0;
        }
        for (int i = start; i < 26; i++) {
            index.set(i, index.get(i) + 1);
        }
    }

}