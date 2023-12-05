package dictionary.gui.request;

import dictionary.TaskRunner;
import dictionary.db.DatabaseRequestHandle;
import dictionary.gui.control.LibraryTypeController;

public class LibraryTypeRequestDelegator {
    /**
     * Insert dictionary from file.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean insertDictionaryFromFile(String filePath) {
        return TaskRunner.loadData_changeStage(filePath, LibraryTypeController::switchToApp);
    }

    /**
     * Insert dictionary from database.
     */
    public static void insertDictionaryFromDatabase() {
        TaskRunner.loadData_changeStage(DatabaseRequestHandle::loadLocalDictionary, LibraryTypeController::switchToApp);
    }
}
