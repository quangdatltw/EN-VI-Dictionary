package dictionary.api;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The type Text to speech api.
 */
public class TextToSpeechAPI {
    @SuppressWarnings("FieldMayBeFinal")
    private static Scanner scn = new Scanner(System.in);
    private static final List<String> urlList = new ArrayList<>();
    private static String language = "en";


    /**
     * Begin convert to speech cmd.
     */
    public void cmdConvert() {
        String sentence = "";
        System.out.print("""
                        Text to speech.
                        Press ENTER when finish typing
                        
                        Sentence:\s""");
        try {
            sentence = scn.nextLine();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
        String apiUrl = getApiUrl(sentence, TranslateAPI.detectLanguage(sentence));
        playAudio(apiUrl);
        while (true) {
            System.out.println("""
                    Replay? [Y/N]""");
            if (!scn.nextLine().equalsIgnoreCase("Y")) {
                break;
            }
            try {
                playAudio(apiUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void playAudio(String api) {
        try {
            URI uri = new URI(api);
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream audio = con.getInputStream();
            new Player(audio).play();
        } catch (Exception ignore) {
        }
    }

    public static void setLanguage(String fromL) {
        language = fromL;
    }

    public static void splitSentence(String sentence) {
        urlList.clear();
        while (sentence.length() > 200) {
            for (int i = 180; i < sentence.length(); i++) {
                if (sentence.charAt(i) == ' ') {
                    String part = sentence.substring(0, i);
                    sentence = sentence.substring(i);
                    urlList.add(getApiUrl(part, language));
                    break;
                }
            }
        }
        urlList.add(getApiUrl(sentence, language));
    }

    /**
     * Gets api url.
     *
     * @param sentence the sentence
     * @param language the language
     * @return the api url
     */
    private static String getApiUrl(String sentence, String language) {
        String apiUrl = "";
        try {
             apiUrl = "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                     + language
                     + "&client=tw-ob&q="
                     + URLEncoder.encode(sentence, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getting voices");
        }
        return apiUrl;
    }

    public static MediaPlayer getMediaPlayerForWord(String word, String language) {
        String api = getApiUrl(word, language);
        Media media = new Media(api);
        return new MediaPlayer(media);
    }


    public static List<MediaPlayer> getMediaPlayerList() {
        List<MediaPlayer> mediaPlayers = new ArrayList<>();
        for (String i : urlList) {
            mediaPlayers.add(new MediaPlayer(new Media(i)));
        }

        return mediaPlayers;
    }


}
