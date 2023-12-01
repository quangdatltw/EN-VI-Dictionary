package dictionary.gui.request;

import dictionary.TaskRunner;
import dictionary.db.DatabaseRequestHandle;
import dictionary.gui.control.InputDataController;

public class InputDataRequestDelegator {
    /**
     * Insert dictionary from file.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean insertDictionaryFromFile(String filePath) {
        return TaskRunner.loadData_changeStage(filePath, InputDataController::switchToApp);
    }

    /**
     * Insert dictionary from database.
     */
    public static void insertDictionaryFromDatabase() {
        TaskRunner.loadData_changeStage(DatabaseRequestHandle::loadLocalDictionary, InputDataController::switchToApp);
    }
}
