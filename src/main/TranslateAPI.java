package main;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.sound.sampled.*;

public class TranslateAPI {
    @SuppressWarnings("FieldMayBeFinal")
    private static Scanner scn = new Scanner(System.in);

    public static void sentenceTranslator() {
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
            System.out.println(getResult(sentence, fromL, targetL));
        }
    }

    private static String getResult(String sentence, String fromL, String targetL) {
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
        sentenceTL = sentenceTL.substring(0,sentenceTL.indexOf("\""));
        return sentenceTL;
    }

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
