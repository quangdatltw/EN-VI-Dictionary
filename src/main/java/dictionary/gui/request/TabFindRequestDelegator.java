package dictionary.gui.request;

import dictionary.GeneralRequestResolve;

import java.util.List;

public class TabFindRequestDelegator extends GeneralRequestDelegator {
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
