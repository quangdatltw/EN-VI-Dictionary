package dictionary.db;

import javafx.concurrent.Task;

public class TaskRunner {

    public static void loadData_changeStage(Runnable taskRunnable, Runnable nextTaskRunnable) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                taskRunnable.run();
                return null;
            }
        };
        task.setOnSucceeded(event -> nextTaskRunnable.run());
        new Thread(task).start();
    }

    public static boolean loadData_changeStage(String filePath, Runnable nextTaskRunnable) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                InterfaceRequestDelegate.insertDictionaryFromFile(filePath);
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> nextTaskRunnable.run());
        try {
            task.get();
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    public static void translate(String sentence, String fromL, String toL) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                TranslateAPI.translate(sentence, fromL, toL);
                return null;
            }
        };
        new Thread(task).start();
    }

}