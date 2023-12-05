package dictionary.gui.request;

import dictionary.TaskRunner;
import dictionary.api.TextToSpeechAPI;
import dictionary.api.TranslateAPI;
import javafx.scene.media.MediaPlayer;

import java.util.List;

public class TabGGTranslateRequestDelegator {
    private static String previousSentence = "";

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

    /**
     * Create media player list.
     *
     * @param sentence the sentence
     * @param language the language
     * @param run      the run
     */
    public static void createMediaPlayerList(String sentence, String language, Runnable run) {
        if (previousSentence.equals(sentence)) {
            run.run();
        } else {
            previousSentence = sentence;
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
}
