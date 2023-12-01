package dictionary;

import dictionary.api.TextToSpeechAPI;
import dictionary.api.TranslateAPI;
import javafx.concurrent.Task;

/**
 * The type Task runner.
 */
public class TaskRunner {

    /**
     * Load database library and switch Stage.
     *
     * @param taskRunnable     the task runnable
     * @param nextTaskRunnable the next task runnable
     */
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

    /**
     * Load external library and switch Stage.
     *
     * @param filePath         the file path
     * @param nextTaskRunnable the next task runnable
     * @return the boolean
     */
    public static boolean loadData_changeStage(String filePath, Runnable nextTaskRunnable) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                if (GeneralRequestResolve.importDFile(filePath)) {
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

    /**
     * Translate sentence.
     *
     * @param sentence the sentence
     * @param fromL    the froml
     * @param toL      the tol
     * @param runnable the runnable
     */
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

    /**
     * Convert Text to Speech.
     *
     * @param sentence the sentence
     * @param language the language
     * @param runnable the runnable
     */
    public static void convertTTS(String sentence, String language, Runnable runnable) {

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                TextToSpeechAPI.setLanguage(language);
                TextToSpeechAPI.splitSentence(sentence);
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event ->  runnable.run());
    }

}