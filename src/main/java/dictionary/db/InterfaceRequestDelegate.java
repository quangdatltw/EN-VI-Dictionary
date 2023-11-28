package dictionary.db;


import javafx.scene.media.MediaPlayer;

import java.util.List;

public class InterfaceRequestDelegate {
    private static LocalDictionaryRequestHandle librarian = new LocalDictionaryRequestHandle();

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
        return GeneralRequestResolve.importDFile(filePath);
    }

    /**
     * Speak given sentence.
     *
     * @param sentence the sentence
     * @param language the language
     */
    public static MediaPlayer getMediaPlayer(String sentence, String language) {
        String apiUrl = TextToSpeechAPI.getApiUrl(sentence, language);
        return TextToSpeechAPI.getMedia(apiUrl);
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
        TranslateAPI.translate(sentence, fromL, toL);
        return TranslateAPI.getResult();
    }
}
