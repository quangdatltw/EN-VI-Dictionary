package dictionary.db;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.List;

public class InterfaceInputHandle {
    /**
     * Lookup word in LocalDictionary.
     *
     * @param word the word
     * @return the string
     */
    public static String lookup(String word) {
        return LocalDictionary.getDefinition(word);
    }

    /**
     * Search list of word match given prefix.
     *
     * @param prefix the prefix
     * @return the list
     */
    public static List<String> search(String prefix) {
        return InputHandle.inputSearch(prefix);
    }

    /**
     * Add word to LocalDictionary.
     *
     * @param word the word
     * @param def  the def
     * @return the boolean
     */
    public static boolean isWordAdded(String word, String def) {
        if (!LocalDictionary.checkWordExistence(word)) {
            LocalDictionary.addWord(word, def);
            DictionaryDatabase.addWord(word, def);
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
        LocalDictionary.updateWord(word, def);
    }

    /**
     * Remove word from LocalDictionary.
     *
     * @param word the word
     */
    public static void remove(String word) {
        LocalDictionary.removeWord(word);
    }

    /**
     * Insert dictionary from file.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean insertDictionaryFromFile(String filePath) {
        return InputHandle.inputFile(filePath);
    }

    /**
     * Speak given sentence.
     *
     * @param sentence the sentence
     * @param language the language
     */
    public static MediaPlayer getMediaPlayer(String sentence, String language) {
        String apiUrl = TextToSpeechAPI.getApiUrl(sentence, language);
        Media media = new Media(apiUrl);
        return new MediaPlayer(media);
    }

    /**
     * Translate string.
     *
     * @param sentence the sentence
     * @param fromL    the froml
     * @param toL      the tol
     * @return the string
     */
    public static String translate(String sentence, String fromL, String toL) {
        return TranslateAPI.getResult(sentence, fromL, toL);
    }
}
