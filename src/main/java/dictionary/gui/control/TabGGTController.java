package dictionary.gui.control;

import dictionary.gui.request.TabGGTranslateRequestDelegator;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;

public class TabGGTController extends AppController {
    private static MediaPlayer playingMedia;
    private static List<MediaPlayer> mediaPlayerList;
    private static String fromL = "en";
    private static String toL = "vi";
    @FXML
    private TextArea sentenceFromL;
    @FXML
    private TextArea sentenceToL;
    @FXML
    private Button pause;
    @FXML
    private Button translate;
    @FXML
    private Button speakFromL;
    @FXML
    private Button speakToL;

    @FXML
    public void initialize() {
    }

    /**
     * Translate sentence.
     */
    @FXML
    public void startTranslate() {
        String sentence = sentenceFromL.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        TabGGTranslateRequestDelegator.createTranslation(sentence, fromL, toL, this::setTranslated);
        translate.setDisable(true);
    }

    /**
     * Sets translation.
     */
    private void setTranslated() {
        sentenceToL.setText(TabGGTranslateRequestDelegator.getTranslation());
        translate.setDisable(false);
    }

    /**
     * Swap prompt text, text, language type of sentenceFromL and sentenceToL
     */
    @FXML
    public void swapL() {
        String temp = fromL;
        fromL = toL;
        toL = temp;

        temp = sentenceFromL.getPromptText();
        sentenceFromL.setPromptText(sentenceToL.getPromptText());
        sentenceToL.setPromptText(temp);

        temp = sentenceFromL.getText();
        sentenceFromL.setText(sentenceToL.getText());
        sentenceToL.setText(temp);
    }

    /**
     * Speak sentence in sentenceFromL.
     */
    @FXML
    public void speakSentenceFromL() {
        String sentence = sentenceFromL.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        TabGGTranslateRequestDelegator.createMediaPlayerList(sentence, fromL, this::setMediaPlayer);
        speakFromL.setDisable(true);
    }

    /**
     * Speak sentence in sentenceToL.
     */
    @FXML
    public void speakSentenceToL() {
        String sentence = sentenceToL.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        TabGGTranslateRequestDelegator.createMediaPlayerList(sentence, toL, this::setMediaPlayer);
        speakToL.setDisable(true);
    }

    private void setMediaPlayer() {
        stopPlayingMedia();
        mediaPlayerList = TabGGTranslateRequestDelegator.getMediaPlayerList();
        playMediaPlayerList();
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            speakFromL.setDisable(false);
            speakToL.setDisable(false);
        });
        pause.play();
    }
    private  void playMediaPlayerList() {
        for (int i = 0; i < mediaPlayerList.size() - 1; i++) {
            int finalI = i + 1;
            mediaPlayerList.get(i).setOnEndOfMedia(() -> {
                mediaPlayerList.get(finalI).play();
                setPlayingMedia(mediaPlayerList.get(finalI));
            });
        }
        setPlayingMedia(mediaPlayerList.get(0));
        mediaPlayerList.get(0).play();
    }
    private void stopPlayingMedia() {
        if (playingMedia != null) {
            playingMedia.stop();
        }
        playingMedia = null;
        setPauseButtonImg("/icon/pause.png");
    }
    private static void setPlayingMedia(MediaPlayer mediaPlayer) {
        playingMedia = mediaPlayer;
    }
    @FXML
    private void pause_continueMedia() {
        if (playingMedia == null) {
            return;
        }
        if (playingMedia.getStatus() == MediaPlayer.Status.PLAYING) {
            playingMedia.pause();
            setPauseButtonImg("/icon/play.png");
        } else if (playingMedia.getStatus() == MediaPlayer.Status.PAUSED) {
            setPauseButtonImg("/icon/pause.png");
            playingMedia.play();
        }
    }
    private void setPauseButtonImg(String url) {
        Image pauseIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)));
        pause.setGraphic(new ImageView(pauseIcon));
    }

    /**
     * Copy sentence in sentenceFromL.
     */
    @FXML
    public void copySentenceFromL() {
        copyText(sentenceFromL.getText());
    }

    /**
     * Copy sentence in sentenceToL.
     */
    @FXML
    public void copySentenceToL() {
        copyText(sentenceToL.getText());
    }
}
