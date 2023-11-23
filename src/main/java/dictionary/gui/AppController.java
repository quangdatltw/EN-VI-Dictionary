package dictionary.gui;

import dictionary.db.InterfaceInputHandle;
import dictionary.db.LocalDictionary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class AppController {
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
    public void searchPrefix() {
        List<String> wordList = InterfaceInputHandle.dictionarySearcher(searchWord.getText().toLowerCase());
        searchList.getItems().clear();
        suggestWord.clear();
        if (!wordList.isEmpty()) {
            suggestWord.setText(wordList.get(0));
            searchList.getItems().addAll(wordList);
        }
    }

    @FXML
    public void choosenWord() {
        String word = searchWord.getText();
        try {
            word = searchList.getSelectionModel().getSelectedItem();
        } catch (Exception ignore) {
        }
        wordDef.setText(InterfaceInputHandle.dictionaryLookup(word));
    }

    @FXML
    public void searchWordKey(KeyEvent key) {
        if (key.getCode() == KeyCode.TAB) {
            searchWord.setText(searchList.getItems().get(0));
        }
        if (key.getCode() == KeyCode.ENTER) {
            wordDef.setText(InterfaceInputHandle.dictionaryLookup(searchWord.getText()));
        }
    }

    @FXML
    public void speakWord() {
        String word = searchList.getSelectionModel().getSelectedItem();
        if (LocalDictionary.checkWord(word)) {
            InterfaceInputHandle.speaker(word);
        } else {
            word = searchWord.getText();
            if (word.isEmpty() && LocalDictionary.checkWord(word)) {
                InterfaceInputHandle.speaker(searchWord.getText());
            }
        }
    }
}