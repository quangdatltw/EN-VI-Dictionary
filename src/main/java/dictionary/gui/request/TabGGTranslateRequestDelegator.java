package dictionary.gui.request;

import dictionary.TaskRunner;
import dictionary.api.TextToSpeechAPI;
import dictionary.api.TranslateAPI;
import javafx.scene.media.MediaPlayer;

import java.util.List;

public class TabGGTranslateRequestDelegator {
    private static String lastSentence = "";

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

    /**
     * Create media player list.
     *
     * @param sentence the sentence
     * @param language the language
     * @param run      the run
     */
    public static void createMediaPlayerList(String sentence, String language, Runnable run) {
        if (lastSentence.equals(sentence)) {
            run.run();
        } else {
            lastSentence = sentence;
            TaskRunner.convertTTS(sentence, language, run);
        }
    }

    /**
     * Gets media player list.
     *
     * @return the media player list
     */
    public static List<MediaPlayer> getMediaPlayerList() {
        return TextToSpeechAPI.getMediaPlayerList();
    }

    /**
     * Create translation.
     *
     * @param sentence the sentence
     * @param fromL    the froml
     * @param toL      the tol
     * @param set      the set
     */
    public static void createTranslation(String sentence, String fromL, String toL, Runnable set) {
        TaskRunner.translate(sentence, fromL, toL, set);
    }

    /**
     * Gets translation.
     *
     * @return the translation
     */
    public static String getTranslation() {
        return TranslateAPI.getResult();
    }
}
