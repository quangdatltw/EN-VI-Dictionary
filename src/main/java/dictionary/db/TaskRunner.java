package dictionary.db;

import javafx.concurrent.Task;

public class TaskRunner {

    public static void runTask(Runnable taskRunnable, Runnable nextTaskRunnable) {
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

}