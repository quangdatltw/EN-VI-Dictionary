package dictionary.gui.request;

import dictionary.GeneralRequestResolve;
import dictionary.api.TextToSpeechAPI;
import javafx.scene.media.MediaPlayer;

import java.util.List;

public class TabFindRequestDelegator extends RequestDelegator {
    /**
     * Search list of words with prefix.
     *
     * @param prefix the prefix
     * @return the list
     */
    public static List<String> search(String prefix) {
        return GeneralRequestResolve.getSearchedList(prefix);
    }

    /**
     * Gets media player.
     *
     * @param word     the word
     * @param language the language
     * @return the media player
     */
    public static MediaPlayer getMediaPlayer(String word, String language) {
        return TextToSpeechAPI.getMediaPlayerForWord(word, language);
    }
}
