package dictionary.gui;

import dictionary.db.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;

import java.util.List;


public class AppController {
    /* All Tab method *///////////////////////////////////////////////////
    private static MediaPlayer mediaPlayer;
    @FXML
    private void initialize() {
        wordHistory.setItems(WordHistory.getHistory());
        searchWord.textProperty().addListener((observable, oldValue, newValue)
                -> searchWord.setText(newValue.toLowerCase()));
    }

    private static void playSound(String string, String lang) {
        mediaPlayer = InterfaceRequestDelegate.getMediaPlayer(string, lang);
        mediaPlayer.play();
    }

    private static void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private static void pauseSong() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
        }
    }



    /* Controller for Tab - Find */////////////////////////////////////////////////

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

    /**
     * Clear selection in List-View Click anchor pane.
     */
    @FXML
    public void clickAnchorPane() {
        searchList.getSelectionModel().clearSelection();
    }

    /**
     * Search prefix get from searchWord
     * -> display searched word list.
     * Set word suggestWord.
     */
    @FXML
    public void searchPrefix() {
        List<String> wordList = InterfaceRequestDelegate.search(searchWord.getText().toLowerCase());
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
            word = searchWord.getText().toLowerCase();
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
        String word = searchWord.getText().toLowerCase();
        if (key.getCode() == KeyCode.TAB) {
            searchWord.setText(searchList.getItems().get(0));
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
        stopSound();
        String word = searchList.getSelectionModel().getSelectedItem();
        if (word != null) {
            playSound(word, "en");
        } else {
            word = searchWord.getText().toLowerCase();
            if (InterfaceRequestDelegate.checkWord(word)) {
                playSound(searchWord.getText(), "en");
            }
        }
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
        searchPrefix();
        wordDef.setText(InterfaceRequestDelegate.lookup(word));
        WordHistory.addWord(word);
    }

    /* Controller for Tab - Google Translate *////////////////////////////////////////
    private static String fromL = "en";
    private static String toL = "vi";
    @FXML
    private TextArea sentenceFromL;
    @FXML
    private TextArea sentenceToL;

    /**
     * Start translate sentence from "sentenceFromL" to "sentenceToL".
     */
    @FXML
    public void startTranslate() {
        String sentence = sentenceFromL.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        sentence = sentence.replace("\n", " * ");
        sentence = InterfaceRequestDelegate.translate(sentence, fromL, toL);
        sentenceToL.setText(sentence.replace(" * ", "\n"));
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
        stopSound();
        String sentence = sentenceFromL.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        playSound(sentence, fromL);
    }

    /**
     * Speak sentence in sentenceToL.
     */
    @FXML
    public void speakSentenceToL() {
        stopSound();
        String sentence = sentenceToL.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        playSound(sentence, toL);
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

    private void copy(String sentence) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(sentence);
        clipboard.setContent(content);
    }


    /* Controller for Tab - Add/Remove/Update *////////////////////////////////////////


}