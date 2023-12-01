package dictionary.gui.request;

import dictionary.db.LocalDictionaryRequestHandle;

public class TabARURequestDelegator {
    static final LocalDictionaryRequestHandle librarian = new LocalDictionaryRequestHandle();

    public static boolean checkWord(String word) {
        return librarian.checkWordExistence(word);
    }

    public static boolean addWord(String word, String def) {
        if (!librarian.checkWordExistence(word)) {
            librarian.addWord(word, def);
            return true;
        } else {
            return false;
        }
    }

    public static void update(String word, String def) {
        librarian.updateWord(word, def);
    }

    public static void remove(String word) {
        librarian.removeWord(word);
    }
}
