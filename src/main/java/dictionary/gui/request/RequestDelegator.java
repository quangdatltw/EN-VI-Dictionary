package dictionary.gui.request;

import dictionary.db.LocalDictionaryRequestHandle;

public class RequestDelegator {
    static final LocalDictionaryRequestHandle librarian = new LocalDictionaryRequestHandle();

    /**
     * Lookup word definition in LocalDictionary.
     *
     * @param word the word
     * @return the string
     */
    public static String lookup(String word) {
        return librarian.getDefinition(word);
    }
}
