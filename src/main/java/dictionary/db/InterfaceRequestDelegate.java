package dictionary.db;


import dictionary.gui.AppController;
import dictionary.gui.InputDataController;
import javafx.scene.media.MediaPlayer;

import java.util.List;

public class InterfaceRequestDelegate {
    private static LocalDictionaryRequestHandle librarian = new LocalDictionaryRequestHandle();
    private static TextToSpeechAPI converter = new TextToSpeechAPI();

    /**
     * Lookup word in LocalDictionary.
     *
     * @param word the word
     * @return the string
     */
    public static String lookup(String word) {
        return librarian.getDefinition(word);
    }

    /**
     * Search list of word match given prefix.
     *
     * @param prefix the prefix
     * @return the list
     */
    public static List<String> search(String prefix) {
        return GeneralRequestResolve.getSearchedList(prefix);
    }


    public static boolean checkWord(String word) {
        return librarian.checkWordExistence(word);
    }
    /**
     * Add word to LocalDictionary.
     *
     * @param word the word
     * @param def  the def
     * @return the boolean
     */
    public static boolean addWord(String word, String def) {
        if (!librarian.checkWordExistence(word)) {
            librarian.addWord(word, def);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update word in LocalDictionary.
     *
     * @param word the word
     * @param def  the def
     */
    public static void update(String word, String def) {
        librarian.updateWord(word, def);
    }

    /**
     * Remove word from LocalDictionary.
     *
     * @param word the word
     */
    public static void remove(String word) {
        librarian.removeWord(word);
    }

    /**
     * Insert dictionary from file.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean insertDictionaryFromFile(String filePath) {
        return TaskRunner.loadData_changeStage(filePath, InputDataController::switchToApp);
    }

    public static void insertDictionaryFromDatabase() {
        TaskRunner.loadData_changeStage(DatabaseRequestHandle::loadLocalDictionary, InputDataController::switchToApp);
    }

    /**
     * Speak given sentence.
     *
     * @param sentence the sentence
     * @param language the language
     */
    public static List<MediaPlayer> getMediaPlayerList(String sentence, String language) {
        return TaskRunner.convertTTS(sentence, language);
    }

    public static MediaPlayer getMediaPlayer(String word, String language) {
        return converter.getMediaPlayerForWord(word, language);
    }

    /**
     * Translate string.
     *
     * @param sentence the sentence
     * @param fromL    the froml
     * @param toL      the tol
     */
    public static void translate(String sentence, String fromL, String toL, Runnable set) {
        TaskRunner.translate(sentence, fromL, toL, set);
    }
    public static String getTranslated() {
        return TranslateAPI.getResult();
    }
}
