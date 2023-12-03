package dictionary.gui.request;

/**
 * The type Tab aru request delegator.
 */
public class TabARURequestDelegator extends GeneralRequestDelegator {

    /**
     * Check word boolean.
     *
     * @param word the word
     * @return the boolean
     */
    public static boolean checkWord(String word) {
        return librarian.checkWordExistence(word);
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
