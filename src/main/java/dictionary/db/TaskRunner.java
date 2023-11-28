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
                if (!GeneralRequestResolve.importDFile(filePath)) {
                    throw new RuntimeException();
                }
                return null;
            }
        };
        new Thread(task).start();

        try {
            task.get();
            task.setOnSucceeded(event -> nextTaskRunnable.run());
            return true;

        } catch (Exception ignore) {
            return false;
        }
    }

    public static void translate(String sentence, String fromL, String toL, Runnable runnable) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                TranslateAPI.translate(sentence, fromL, toL);
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> runnable.run());
    }

    public static TextToSpeechAPI convertTTS(String sentence, String language, Runnable runnable) {
        TextToSpeechAPI convert = new TextToSpeechAPI();
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                convert.setLanguage(language);
                convert.splitSentence(sentence);
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event ->  runnable.run());
        return convert;
    }

}