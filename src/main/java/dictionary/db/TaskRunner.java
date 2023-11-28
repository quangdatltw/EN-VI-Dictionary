package dictionary.db;

import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

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

    public static List<MediaPlayer> convertTTS(String sentence, String language) {
        TextToSpeechAPI convert = new TextToSpeechAPI();
        List<Media> mediaList;
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                convert.setLanguage(language);
                convert.splitSentence(sentence);
                return null;
            }
        };
        new Thread(task).start();

        mediaList = convert.getMediaList();
        List<MediaPlayer> playList = new ArrayList<>();
        for (Media media : mediaList) {
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            playList.add(mediaPlayer);
        }
        for (MediaPlayer md : playList) {
            Task<Void> taskk = new Task<>() {
                @Override
                protected Void call() {
                    md.play();
                    return null;
                }
            };
            new Thread(taskk).start();
        }

        return playList;
    }

}