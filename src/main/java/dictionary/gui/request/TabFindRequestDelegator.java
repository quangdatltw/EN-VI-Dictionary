package dictionary.gui.request;

import dictionary.GeneralRequestResolve;

import java.util.List;

public class TabFindRequestDelegator {
    /**
     * Lookup word definition in LocalDictionary.
     *
     * @param word the word
     * @return the string
     */
    public static String lookup(String word) {
        return TabARURequestDelegator.librarian.getDefinition(word);
    }

    /**
     * Search list of words with prefix.
     *
     * @param prefix the prefix
     * @return the list
     */
    public static List<String> search(String prefix) {
        return GeneralRequestResolve.getSearchedList(prefix);
    }
}
