package dictionary.gui;

import dictionary.db.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class AppController {
    @FXML
    private void initialize() {
        wordHistory.setItems(WordHistory.getHistory());
        searchWord.textProperty().addListener((observable, oldValue, newValue)
                -> searchWord.setText(newValue.toLowerCase()));
    }
    /** Controller for Tab - Find */
    @FXML
    public ComboBox<String> wordHistory;
    @FXML
    public ListView<String> searchList;
    @FXML
    public TextField searchWord;
    @FXML
    public TextField suggestWord;
    @FXML
    public TextArea wordDef;
    @FXML
    public Button speaker;

    @FXML
    public void clickAnchorPane() {
        searchList.getSelectionModel().clearSelection();

    }

    @FXML
    public void searchPrefix() {
        List<String> wordList = InterfaceInputHandle.search(searchWord.getText().toLowerCase());
        searchList.getItems().clear();
        suggestWord.clear();
        if (!wordList.isEmpty()) {
            suggestWord.setText(wordList.get(0));
            searchList.getItems().addAll(wordList);
        }
    }

    @FXML
    public void findChosenWord() {
        String word = searchList.getSelectionModel().getSelectedItem();
        if (word == null) {
            word = searchWord.getText().toLowerCase();
        }
        WordHistory.addWord(word);
        wordDef.setText(InterfaceInputHandle.lookup(word));
    }

    @FXML
    public void searchWordKey(KeyEvent key) {
        String word = searchWord.getText().toLowerCase();
        if (key.getCode() == KeyCode.TAB) {
            searchWord.setText(searchList.getItems().get(0));
        }
        if (key.getCode() == KeyCode.ENTER) {
            wordDef.setText(InterfaceInputHandle.lookup(word));
            WordHistory.addWord(word);
        }
    }

    @FXML
    public void speakWord() {
        String word = searchList.getSelectionModel().getSelectedItem();
        if (word != null) {
            InterfaceInputHandle.speakSentence(word, "en");
        } else {
            word = searchWord.getText().toLowerCase();
            if (LocalDictionary.checkWordExistence(word)) {
                InterfaceInputHandle.speakSentence(searchWord.getText(), "en");
            }
        }
    }

    @FXML
    public void openWordHistory() {
        wordHistory.setItems(FXCollections.observableArrayList(WordHistory.getHistory()));
    }

    @FXML
    public void getWordHistory() {
        String word = wordHistory.getSelectionModel().getSelectedItem();
        searchWord.setText(word);
        if (word == null) return;
        searchPrefix();
        wordDef.setText(LocalDictionary.getDefinition(word));
        WordHistory.addWord(word);
    }

    /** Controller for Tab - Google Translate */
    private static String fromL = "en";
    private static String toL = "vi";
    @FXML
    public TextArea sentenceFromL;
    @FXML
    public TextArea sentenceToL;

    @FXML
    public void startTranslate() {
        String sentence = sentenceFromL.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        sentence = sentence.replace("\n", " * ");
        sentence = InterfaceInputHandle.translate(sentence, fromL, toL);
        sentenceToL.setText(sentence.replace(" * ", "\n"));
    }

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

    @FXML
    public void speakSentenceFromL() {
        String sentence = sentenceFromL.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        InterfaceInputHandle.speakSentence(sentence, fromL);
    }
    @FXML
    public void speakSentenceToL() {
        String sentence = sentenceToL.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        InterfaceInputHandle.speakSentence(sentence, toL);
    }

    @FXML
    public void copySentenceFromL() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(sentenceFromL.getText());
        clipboard.setContent(content);
    }

    @FXML
    public void copySentenceToL() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(sentenceToL.getText());
        clipboard.setContent(content);
    }
}