package dictionary.db;


import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



public class TextToSpeechAPI {
    @SuppressWarnings("FieldMayBeFinal")
    private static Scanner scn = new Scanner(System.in);

    public static void textToSpeech() {
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
        String apiUrl = getApiUrl(sentence);
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

    public static void textToSpeech(String sentence) {
        try {
            playAudio(getApiUrl(sentence));
        } catch (Exception ignore) {
        }
    }

    public static String getApiUrl(String sentence) {
        //String detectL = TranslateAPI.detectLanguage(sentence);
        String apiUrl = "";
        try {
             apiUrl = "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                     + "en"
                     + "&client=tw-ob&q="
                     + URLEncoder.encode(sentence, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getting voices");
        }
        return apiUrl;
    }

    private static void playAudio(String api) {
        try {
            URI uri = new URI(api);
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream audio = con.getInputStream();
            new Player(audio).play();
        } catch (Exception ignore) {
        }
    }
}
