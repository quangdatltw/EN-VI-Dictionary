package main;


import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;



public class TextToSpeechAPI {
    @SuppressWarnings("FieldMayBeFinal")
    private static Scanner scn = new Scanner(System.in);

    public static void textToSpeech() throws IOException, LineUnavailableException, UnsupportedAudioFileException, URISyntaxException {
        byte[] audioData = getApiUrl();
        playAudio(audioData);
        String replay;
        while (true) {
            System.out.println("""
                    Replay? [Y/N]""");
            replay = scn.nextLine();
            if (replay.equals("N")) {
                break;
            }
            playAudio(audioData);
        }
    }

    private static byte[] getApiUrl() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
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
        //String detectL = TranslateAPI.detectLanguage(sentence);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://text-to-speech27.p.rapidapi.com/speech?text=hello%20world&lang=en-us"))
                .header("X-RapidAPI-Key", "296277a360msh3e84e9e818459c4p1dfa46jsne15e968d0106")
                .header("X-RapidAPI-Host", "text-to-speech27.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        URL connection = response.uri().toURL();
        InputStream inURL = connection.openStream();
        AudioInputStream stream = AudioSystem.getAudioInputStream(connection);
        Clip clip = AudioSystem.getClip();
        clip.open(stream);
        clip.start();
        clip.drain();
        clip.close();
        return inURL.readAllBytes();
    }

    private static void playAudio(byte[] audioData) throws LineUnavailableException {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false);
        Clip clip = AudioSystem.getClip();
        clip.open(format, audioData, 0, audioData.length);
        clip.start();
        clip.drain();
        clip.close();
    }
}
