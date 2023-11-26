package dictionary.db;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.List;
public class InterfaceInputHandle {
    public static String lookup(String word) {
        return LocalDictionary.getDefinition(word);
    }

    public static List<String> search(String prefix) {
        return InputHandle.inputSearch(prefix);
    }

    public static boolean addWord(String word, String def) {
        if (!LocalDictionary.checkWordExistence(word)) {
            LocalDictionary.addWord(word, def);
            DictionaryDatabase.putWord(word, def);
            return true;
        } else {
            return false;
        }
    }

    public static void update(String word, String def) {
        LocalDictionary.updateWord(word, def);
    }

    public static void remove(String word) {
        LocalDictionary.removeWord(word);
    }

    public static boolean insertDictionaryFromFile(String filePath) {
        return InputHandle.inputFile(filePath);
    }

    public static void speakSentence(String sentence, String language) {
        String apiUrl = TextToSpeechAPI.getApiUrl(sentence, language);
        Media media = new Media(apiUrl);

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
