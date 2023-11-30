package dictionary.gui;

import dictionary.db.InterfaceRequestDelegate;
import dictionary.db.WordHistory;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;


public class AppController {
    /* All Tab method *///////////////////////////////////////////////////
    private static MediaPlayer playingMedia;

    private static List<MediaPlayer> mediaPlayerList;

    @FXML
    private void initialize() {
        wordHistory.setItems(WordHistory.getHistory());
        searchWord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z'\\-(]*")) {
                searchWord.setText(oldValue);
                return;
            }
            if (isNewValueUsable(newValue)) {
                searchWord.setText(newValue.toLowerCase());
            }
        });
    }

    private boolean isNewValueUsable(String newValue) {
        return !(newValue == null)
                && !newValue.isEmpty()
                && !searchWord.getText().equals(newValue.toLowerCase());
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

    private void copy(String sentence) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(sentence);
        clipboard.setContent(content);
    }

    private void endPlayingMedia() {
        if (playingMedia != null) {
            playingMedia.stop();
        }
        playingMedia = null;
        setPauseButtonImg("/icon/pause.png");
    }

    private void setPauseButtonImg(String url) {
        Image pauseIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)));
        pause.setGraphic(new ImageView(pauseIcon));
    }


    /* Controller for Tab - Find *////////////////////////////////////////////////////
    /*
     *
     *
     *
     *
     *
     *
     *
     */

    @FXML
    private ComboBox<String> wordHistory;
    @FXML
    private ListView<String> searchList;
    @FXML
    private TextField searchWord;
    @FXML
    private TextField suggestWord;
    @FXML
    private TextArea wordDef;

    private  void playWordSound(String string) {
        playingMedia = InterfaceRequestDelegate.getMediaPlayer(string, "en");
        playingMedia.play();
    }

    /**
     * Clear selection in List-View Click anchor pane.
     */
    @FXML
    public void clickAnchorPane() {
        wordDef.deselect();
        searchList.getSelectionModel().clearSelection();
    }

    /**
     * Search prefix get from searchWord
     * -> display searched word list.
     * Set word suggestWord.
     */
    @FXML
    public void setWordsWithPrefix() {
        List<String> wordList = InterfaceRequestDelegate.search(searchWord.getText());
        searchList.getItems().clear();
        suggestWord.clear();
        if (!wordList.isEmpty()) {
            suggestWord.setText(wordList.get(0));
            searchList.getItems().addAll(wordList);
        }
    }

    /**
     * Get chosen word in searchWord or searchList(prioritised)
     * -> display definition
     * Add word to WordHistory
     */
    @FXML
    public void findChosenWord() {
        String word = searchList.getSelectionModel().getSelectedItem();
        if (word == null) {
            word = searchWord.getText();
        }
        if (word == null) {
            word = "";
        }
        WordHistory.addWord(word);
        wordDef.setText(InterfaceRequestDelegate.lookup(word));
    }

    /**
     * TAB: Set suggest word for searchWord.
     * ENTER: Get word in searchWord and display definition
     * Add word to WordHistory.
     *
     * @param key the key
     */
    @FXML
    public void searchWordKey(KeyEvent key) {
        String word = searchWord.getText();
        if (key.getCode() == KeyCode.TAB) {
            if (!searchList.getItems().isEmpty()) {
                searchWord.setText(searchList.getItems().get(0));
            }
        }
        if (key.getCode() == KeyCode.ENTER) {
            wordDef.setText(InterfaceRequestDelegate.lookup(word));
            WordHistory.addWord(word);
        }
    }

    /**
     * Get chosen word in searchWord or searchList(prioritised)
     * -> play sound
     */
    @FXML
    public void speakWord() {
        endPlayingMedia();
        String word = searchList.getSelectionModel().getSelectedItem();
        if (word != null) {
            playWordSound(word);
        } else {
            word = searchWord.getText();
            if (InterfaceRequestDelegate.checkWord(word)) {
                playWordSound(searchWord.getText());
            }
        }
    }

    @FXML
    public void copyDefinition() {
        copy(wordDef.getText());
    }

    /**
     * Set word search history for wordHistory.
     */
    @FXML
    public void openWordHistory() {
        wordHistory.setItems(FXCollections.observableArrayList(WordHistory.getHistory()));
    }

    /**
     * Gets word from wordHistory and display definition
     * Add word to WordHistory
     */
    @FXML
    public void getWordHistory() {
        String word = wordHistory.getSelectionModel().getSelectedItem();
        searchWord.setText(word);
        if (word == null) return;
        setWordsWithPrefix();
        wordDef.setText(InterfaceRequestDelegate.lookup(word));
        WordHistory.addWord(word);
    }

    /* Controller for Tab - Google Translate */////////////////////////////////////////////////
    /*
    *
    *
    *
    *
    *
    *
    *
     */

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
    public void setMediaPlayer() {
        endPlayingMedia();
        mediaPlayerList = InterfaceRequestDelegate.getMediaPlayerList();
        playMediaPlayerList();
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            speakFromL.setDisable(false);
            speakToL.setDisable(false);
        });
        pause.play();
    }
    private static void setPlayingMedia(MediaPlayer mediaPlayer) {
        playingMedia = mediaPlayer;
    }

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

    /**
     * Start translate sentence from "sentenceFromL" to "sentenceToL".
     */
    @FXML
    public void startTranslate() {
        String sentence = sentenceFromL.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        InterfaceRequestDelegate.translate(sentence, fromL, toL, this::setTranslated);
        translate.setDisable(true);
    }
    public void setTranslated() {
        sentenceToL.setText(InterfaceRequestDelegate.getTranslated());
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
        InterfaceRequestDelegate.createMediaPlayerList(sentence, fromL, this::setMediaPlayer);
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
        InterfaceRequestDelegate.createMediaPlayerList(sentence, toL, this::setMediaPlayer);
        speakToL.setDisable(true);
    }

    /**
     * Copy sentence in sentenceFromL.
     */
    @FXML
    public void copySentenceFromL() {
        copy(sentenceFromL.getText());
    }

    /**
     * Copy sentence in sentenceToL.
     */
    @FXML
    public void copySentenceToL() {
        copy(sentenceToL.getText());
    }




    /* Controller for Tab - Add/Remove/Update *////////////////////////////////////////


}