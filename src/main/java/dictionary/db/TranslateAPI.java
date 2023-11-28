package dictionary.db;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class TranslateAPI {
    @SuppressWarnings("FieldMayBeFinal")
    private static Scanner scn = new Scanner(System.in);

    private static String result = "";

    /**
     * Begin translate cmd.
     */
    public static void cmdTranslate() {
        String sentence = "";
        String fromL = "en";
        String targetL = "vi";
        while (true) {
            System.out.println("\n[0] Exit");
            System.out.println("Press ENTER when finish typing");
            System.out.print("Sentence: ");
            try {
                sentence = scn.nextLine();
            } catch (NullPointerException n) {
                n.printStackTrace();
            }
            if (sentence.equals("0")) {
                break;
            }
            if (sentence.length() <= 1 && !sentence.equalsIgnoreCase("I")) {
                System.out.println("Sentence need at least 2 character");
                continue;
            }
            String detectL = detectLanguage(sentence);
            if (!fromL.equals(detectL)) {
                System.out.println("This is not English");
                fromL = detectL;
            }
            System.out.println("Translate to Vietnamese:");
            translate(sentence, fromL, targetL);
            System.out.println(result);
        }
    }

    /**
     * Gét translated sentence.
     *
     */
    public static String getResult() {
        return result;
    }

    /**
     * Translate sentence.
     *
     * @param sentence the sentence
     * @param fromL    the froml
     * @param targetL  the targetl
     */
    public static void translate(String sentence, String fromL, String targetL) {
        HttpResponse<String> response = null;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate113.p.rapidapi.com/api/v1/translator/text"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "9d0b864337msh34f4c5473a7c77ap1638d4jsna926f1f3a1bb")
                .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("from=" + fromL + "&to=" + targetL + "&text=" + sentence))
                .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        String sentenceTL = "Can't translate the sentence, sorry :>";
        if (response != null) {
            sentenceTL = response.body();
        }

        sentenceTL = sentenceTL.substring(sentenceTL.indexOf("trans\":\"") + 8);
        sentenceTL = sentenceTL.substring(0,sentenceTL.indexOf("\"}"));
        result = sentenceTL;
    }

    /**
     * Detect language of sentence.
     *
     * @param sentence the sentence
     * @return the string
     */
    public static String detectLanguage(String sentence) {
        HttpResponse<String> response = null;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate113.p.rapidapi.com/api/v1/translator/detect-language"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "9d0b864337msh34f4c5473a7c77ap1638d4jsna926f1f3a1bb")
                .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("text=" + sentence))
                .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String detectL = "en";
        if (response != null) {
            detectL = response.body();
        }
        detectL = detectL.substring(detectL.indexOf("source_lang_code\":\"") + 19, detectL.lastIndexOf("}") - 1);
        return detectL;
    }
}
