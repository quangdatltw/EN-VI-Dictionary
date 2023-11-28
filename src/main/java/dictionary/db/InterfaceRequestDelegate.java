package dictionary.db;


import dictionary.gui.InputDataController;
import javafx.concurrent.Task;
import javafx.scene.media.MediaPlayer;

import java.util.List;

public class InterfaceRequestDelegate {
    private static LocalDictionaryRequestHandle librarian = new LocalDictionaryRequestHandle();
    private static TextToSpeechAPI converter = new TextToSpeechAPI();

    public static String lookup(String word) {
        return librarian.getDefinition(word);
    }

    public static List<String> search(String prefix) {
        return GeneralRequestResolve.getSearchedList(prefix);
    }


    public static boolean checkWord(String word) {
        return librarian.checkWordExistence(word);
    }
    public static boolean addWord(String word, String def) {
        if (!librarian.checkWordExistence(word)) {
            librarian.addWord(word, def);
            return true;
        } else {
            return false;
        }
    }

    public static void update(String word, String def) {
        librarian.updateWord(word, def);
    }

    public static void remove(String word) {
        librarian.removeWord(word);
    }

    public static boolean insertDictionaryFromFile(String filePath) {
        return TaskRunner.loadData_changeStage(filePath, InputDataController::switchToApp);
    }

    public static void insertDictionaryFromDatabase() {
        TaskRunner.loadData_changeStage(DatabaseRequestHandle::loadLocalDictionary, InputDataController::switchToApp);
    }

    public static void createMediaPlayerList(String sentence, String language, Runnable run) {
        converter = TaskRunner.convertTTS(sentence, language, run);
    }
    public static List<MediaPlayer> getMediaPlayerList() {
        List<MediaPlayer> mediaPlayers =  converter.getMediaPlayerList();

        for (MediaPlayer md : mediaPlayers) {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    md.play();
                    return null;
                }
            };
            new Thread(task).start();
        }
        return mediaPlayers;
    }

    public static MediaPlayer getMediaPlayer(String word, String language) {
        return converter.getMediaPlayerForWord(word, language);
    }

    public static void translate(String sentence, String fromL, String toL, Runnable set) {
        TaskRunner.translate(sentence, fromL, toL, set);
    }
    public static String getTranslated() {
        return TranslateAPI.getResult();
    }
}
