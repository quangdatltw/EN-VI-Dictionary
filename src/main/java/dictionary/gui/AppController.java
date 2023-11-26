package dictionary.gui;

import dictionary.db.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class AppController {
    @FXML
    private void initialize() {
        wordHistory.setItems(WordHistory.getHistory());
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
        String word = searchWord.getText();
        try {
            word = searchList.getSelectionModel().getSelectedItem();
        } catch (Exception ignore) {
            word = "";
        }
        WordHistory.addWord(word);
        wordDef.setText(InterfaceInputHandle.lookup(word));
    }

    @FXML
    public void searchWordKey(KeyEvent key) {
        if (key.getCode() == KeyCode.TAB) {
            searchWord.setText(searchList.getItems().get(0));
        }
        if (key.getCode() == KeyCode.ENTER) {
            wordDef.setText(InterfaceInputHandle.lookup(searchWord.getText()));
            WordHistory.addWord(searchWord.getText());
        }
    }

    @FXML
    public void speakWord() {
        String word = searchList.getSelectionModel().getSelectedItem();
        if (LocalDictionary.checkWordExistence(word)) {
            InterfaceInputHandle.speakSentence(word, "en");
        } else {
            word = searchWord.getText();
            if (!word.isEmpty() && LocalDictionary.checkWordExistence(word)) {
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
    public TextArea fromSentence;
    @FXML
    public TextArea toSentence;

    @FXML
    public void startTranslate() {
        String sentence = fromSentence.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        sentence = sentence.replace("\n", " * ");
        sentence = InterfaceInputHandle.translate(sentence, fromL, toL);
        toSentence.setText(sentence.replace(" * ", "\n"));
    }

    @FXML
    public void switchL() {
        String temp = fromL;
        fromL = toL;
        toL = temp;

        temp = fromSentence.getPromptText();
        fromSentence.setPromptText(toSentence.getPromptText());
        toSentence.setPromptText(temp);

        temp = fromSentence.getText();
        fromSentence.setText(toSentence.getText());
        toSentence.setText(temp);
    }

    @FXML
    public void speakSentenceFromL() {
        String sentence = fromSentence.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        InterfaceInputHandle.speakSentence(sentence, fromL);
    }
    @FXML
    public void speakSentenceToL() {
        String sentence = toSentence.getText();
        if (sentence == null || sentence.isEmpty()) {
            return;
        }
        InterfaceInputHandle.speakSentence(sentence, toL);
    }

}