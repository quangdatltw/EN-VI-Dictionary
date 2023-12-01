package dictionary.gui.request;


import dictionary.api.TextToSpeechAPI;
import dictionary.api.TranslateAPI;
import dictionary.GeneralRequestResolve;
import dictionary.TaskRunner;
import dictionary.db.DatabaseRequestHandle;
import dictionary.db.LocalDictionaryRequestHandle;
import dictionary.gui.control.InputDataController;
import javafx.scene.media.MediaPlayer;

import java.util.List;

public class InterfaceRequestDelegate {
    private static String lastSentence = "";
    private static final LocalDictionaryRequestHandle librarian = new LocalDictionaryRequestHandle();

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
        if (lastSentence.equals(sentence)) {
            run.run();
        } else {
            lastSentence = sentence;
            TaskRunner.convertTTS(sentence, language, run);
        }
    }
    public static List<MediaPlayer> getMediaPlayerList() {
        return TextToSpeechAPI.getMediaPlayerList();
    }
    public static MediaPlayer getMediaPlayer(String word, String language) {
        return TextToSpeechAPI.getMediaPlayerForWord(word, language);
    }

    public static void translate(String sentence, String fromL, String toL, Runnable set) {
        TaskRunner.translate(sentence, fromL, toL, set);
    }
    public static String getTranslated() {
        return TranslateAPI.getResult();
    }
}