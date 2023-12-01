package dictionary.gui;

import dictionary.db.InterfaceRequestDelegate;
import dictionary.db.WordHistory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;

import java.util.List;

public class TabFindController {
    private static MediaPlayer playingMedia;
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

    @FXML
    private void initialize() {
        wordHistory.setItems(WordHistory.getHistory());
        searchWord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(newValue == null) && !newValue.matches("[a-zA-Z'\\-(]*")) {
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

    private void copyText(String sentence) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(sentence);
        clipboard.setContent(content);
    }

    private void stopPlayingMedia() {
        if (playingMedia != null) {
            playingMedia.stop();
        }
        playingMedia = null;
    }



    private String findChosenWord() {
        String word = searchList.getSelectionModel().getSelectedItem();
        if (word == null) {
            word = searchWord.getText();
        }
        if (word == null) {
            word = "";
        }
        return word;
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
    public void setPrefixWordList() {
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
    public void setWordDef() {
        String word = findChosenWord();
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
    public void searchWordPressedKey(KeyEvent key) {
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
        stopPlayingMedia();
        String word = findChosenWord();
        playingMedia = InterfaceRequestDelegate.getMediaPlayer(word, "en");
        playingMedia.play();
    }

    @FXML
    public void copyDefinition() {
        copyText(wordDef.getText());
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
        setPrefixWordList();
        wordDef.setText(InterfaceRequestDelegate.lookup(word));
        WordHistory.addWord(word);
    }

}
