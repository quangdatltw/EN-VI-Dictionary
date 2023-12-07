package dictionary.gui.request;

import dictionary.GeneralRequestResolve;

/**
 * The type Tab aru request delegator.
 */
public class TabARURequestDelegator extends RequestDelegator {

    /**
     * Check word boolean.
     *
     * @param word the word
     * @return the boolean
     */
    public static boolean checkWord(String word) {
        return librarian.checkWordExistence(word);
    }

    public static String addMeaning(String wordDef, String wordType, String meaning) {
        return GeneralRequestResolve.addMeaning(wordDef, wordType, meaning);
    }

    public static String addSentence(String wordDef, String addParent, String add, String signature) {
        return GeneralRequestResolve.addSentence(wordDef, addParent, add, signature);
    }
    /**
     * Add word.
     *
     * @param word the word
     * @param def  the def
     */
    public static void addWord(String word, String def) {
        librarian.addWord(word, def);
    }

    /**
     * Update.
     *
     * @param word the word
     * @param def  the def
     */
    public static void update(String word, String def) {
        librarian.updateWord(word, def);
    }

    /**
     * Remove.
     *
     * @param word the word
     */
    public static void remove(String word) {
        librarian.removeWord(word);
    }
}
