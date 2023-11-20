package dictionary.db;


import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



public class TextToSpeechAPI {
    @SuppressWarnings("FieldMayBeFinal")
    private static Scanner scn = new Scanner(System.in);

    public static void textToSpeech() {
        String apiUrl = getApiUrl();
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

    private static String getApiUrl() {
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
        String detectL = TranslateAPI.detectLanguage(sentence);
        String apiUrl = "";
        try {
             apiUrl = "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                     + detectL
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
            URL url = new URL(api);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream audio = con.getInputStream();
            new Player(audio).play();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
