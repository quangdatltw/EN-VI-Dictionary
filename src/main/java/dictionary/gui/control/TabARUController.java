package dictionary.gui.control;

import dictionary.gui.request.TabARURequestDelegator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabARUController {
    private static final TabARUWordDefChangeNode changeHistory = new TabARUWordDefChangeNode();
    private static TabARUNode currentWordDef = new TabARUNode("");
    private static String wordDef = "";
    @FXML
    public void initialize() {
        loadWordTypeList();
        reset();
        searchWord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                reset();
            }
            assert newValue != null;
            searchWord.setText(newValue.toLowerCase());
            checkWord();
            newHistory();
        });

        wordType.valueProperty().addListener((observable, oldValue, newValue) -> newWordType());

    }

    private void loadWordTypeList() {
        wordType.setItems(FXCollections.observableArrayList(wordTypesList));
    }

    private void reset()
    {
        wordDef = "";
        wordType.setValue(null);
        newWordType();
        addWord.setDisable(true);
        updateWord.setDisable(true);
        removeWord.setDisable(true);
        meaning.setDisable(true);
        meaningText.setDisable(true);
        wordType.setDisable(true);
        undoB.setDisable(true);
        redoB.setDisable(true);
    }

    private void newHistory() {
        changeHistory.clear();
        changeHistory.append(wordDef);
        currentWordDef = changeHistory.getNode();
        undoB.setDisable(true);
    }

    private void newWordType() {
        wordMeanings.setValue(null);
        meaningText.clear();
        exampleText.clear();
        exampleText.setDisable(true);
        example.setDisable(true);
        wordMeanings.setDisable(true);
    }

    private void concatDef(String add) {
        wordDef = wordDef + "\n" + add;
        updatePrototypeText();
    }

    private void updatePrototypeText() {
        double scrollPosition = prototypeText.getScrollTop();

        prototypeText.setText(wordDef);

        prototypeText.setScrollTop(scrollPosition);

        changeHistory.insertAfter(currentWordDef, wordDef);
        currentWordDef = changeHistory.getNode();
        if (currentWordDef.prev != null) {
            undoB.setDisable(false);
        }
        redoB.setDisable(true);
    }

    private static final List<String> wordTypesList = new ArrayList<>(
            Arrays.asList(
                    "* Danh từ:",
                    "* Động từ:",
                    "* Tính từ:",
                    "* Trạng từ:",
                    "* Giới từ:",
                    "* Phó từ:",
                    "! Thành ngữ:",
                    "* Nội động từ:",
                    "* Ngoại động từ:"
            )
    );

    @FXML
    private Button addWord;
    @FXML
    private Button updateWord;
    @FXML
    private Button removeWord;
    @FXML
    private Button meaning;
    @FXML
    private Button example;
    @FXML
    private TextField searchWord;
    @FXML
    private ComboBox<String> wordType;
    @FXML
    private ComboBox<String> wordMeanings;
    @FXML
    private TextArea prototypeText;
    @FXML
    private TextArea meaningText;
    @FXML
    private TextArea exampleText;
    @FXML
    private Button undoB;
    @FXML
    private Button redoB;


    public void checkWord() {
        String word = searchWord.getText().trim();
        if (word.isEmpty()) {
            return;
        }
        if (TabARURequestDelegator.checkWord(word)) {
            ifWordDoesExist(word);
        } else {
            ifWordDoesNotExist(word);
        }
    }
    private void ifWordDoesExist(String word) {
        setWordDef(word);

        updateWord.setDisable(false);
        removeWord.setDisable(false);
        wordType.setDisable(false);
    }

    private void setWordDef(String word) {
        wordDef = TabARURequestDelegator.lookup(word);
        updatePrototypeText();
    }

    private void ifWordDoesNotExist(String word) {
        wordDef = word;
        updatePrototypeText();

        addWord.setDisable(false);
        wordType.setDisable(false);
    }

    @FXML
    public void setWordType() {
        String type = wordType.getValue();
        if (type != null && !wordDef.contains(type)) {
            concatDef(type);
        }
        meaning.setDisable(false);
        meaningText.setDisable(false);
        wordMeanings.setDisable(false);
    }

    private String cutWordType() {
        String str = wordDef.substring(wordDef.indexOf(wordType.getValue()));

        if (str.contains("\n*")) {
            str = str.substring(0, str.indexOf("\n*"));
            str = str.substring(0, str.lastIndexOf("\n"));
        }

        if(str.contains("\n!")) {
            str = str.substring(0, str.indexOf("\n!"));
            str = str.substring(0, str.lastIndexOf("\n"));
        }

        return  str;
    }
    @FXML
    public void getWordMeaningList() {
        String str = cutWordType() + "\n";

        List<String> meaningList = new ArrayList<>();
        while (str.contains("-")) {
            str = str.substring(str.indexOf("\n    -") + 1);
            int endPos = str.indexOf("\n");
            String sub = str.substring(str.indexOf("-"), endPos);
            str = str.substring(endPos);
            meaningList.add(sub);
        }

        wordMeanings.setItems(FXCollections.observableArrayList(meaningList));
    }
    @FXML
    public void setWordMeaning() {
        if (wordMeanings.getValue() != null) {
            example.setDisable(false);
            exampleText.setDisable(false);
        }
    }

    @FXML
    public void addMeaning() {
        String set = cutWordType();
        if (meaningText.getText().isEmpty()) {
            return;
        }
        wordDef = wordDef.replace(set, set + "\n    - " + meaningText.getText().replace("\n", ""));
        updatePrototypeText();
    }

    @FXML
    public void addExample() {
        String mean = wordMeanings.getValue();
        if (exampleText.getText().isEmpty()) {
            return;
        }
        wordDef = wordDef.replace(mean, mean + "\n        VD: " + exampleText.getText());
        updatePrototypeText();
    }

    @FXML
    public void undo() {
        currentWordDef = currentWordDef.prev;
        if (currentWordDef.prev == null) {
            undoB.setDisable(true);
        }
        redoB.setDisable(false);

        prototypeText.setText(currentWordDef.data);
        wordDef = currentWordDef.data;
    }

    @FXML
    public void redo() {
        currentWordDef = currentWordDef.next;
        if (currentWordDef.next == null) {
            redoB.setDisable(true);
        }
        undoB.setDisable(false);

        prototypeText.setText(currentWordDef.data);
        wordDef = currentWordDef.data;
    }

    @FXML
    public void addWord() {
        TabARURequestDelegator.addWord(searchWord.getText().trim(), wordDef);
    }

    @FXML
    public void updateWord() {
        TabARURequestDelegator.update(searchWord.getText().trim(), wordDef);
    }

    @FXML
    public void removeWord() {
        TabARURequestDelegator.remove(searchWord.getText().trim());
        searchWord.clear();
        prototypeText.clear();
        reset();
    }

}
