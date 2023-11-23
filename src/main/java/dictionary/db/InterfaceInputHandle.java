package dictionary.db;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.List;
public class InterfaceInputHandle {
    public static String dictionaryLookup(String word) {
        return LocalDictionary.getDefinition(word);

    }

    public static List<String> dictionarySearcher(String prefix) {
        return InputHandle.inputSearch(prefix);
    }

    public static void dictionaryAdd(String word, String def) {

        DictionaryManagement.wordExportToFile(word);
    }

    public static void dictionaryUpdate() {


    }

    public static void dictionaryRemove() {


    }

    public static boolean dictionaryInsertFromFile(String filePath) {
        return InputHandle.inputFile(filePath);
    }

    public static void speaker(String sentence) {
        String apiUrl = TextToSpeechAPI.getApiUrl(sentence);
        // Create a Media object from the InputStream
        Media media = new Media(apiUrl);
        // Create a MediaPlayer
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
