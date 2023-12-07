package dictionary.gui.control.TabARU;

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
        wordExamples.setDisable(true);
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
    private ComboBox<String> wordExamples;
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

    private String cutWordMeanings() {
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
        String str = cutWordMeanings() + "\n";

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
            meaningText.setText(wordMeanings.getValue());
            example.setDisable(false);
            exampleText.setDisable(false);
            wordExamples.setDisable(false);
        }
    }

    @FXML
    public void addMeaning() {
        if (wordMeanings.getValue() == null) {
           wordDef = TabARURequestDelegator.addSentence(wordDef, wordType.getValue(), meaningText.getText().replace("\n", ""), "\n    - ");
        } else {
            wordDef = wordDef.replace(wordMeanings.getValue(), meaningText.getText().replace("\n", ""));
            wordMeanings.setValue(null);
            exampleText.setDisable(true);
            example.setDisable(true);
        }

        meaningText.clear();

        updatePrototypeText();
    }
    @FXML
    private void getWordExampleList() {
        String str = cutWordMeanings() + "\n";
        str = str.substring(str.indexOf(wordMeanings.getValue()) + 1);
        if (str.contains("-")) {
            str = str.substring(0, str.indexOf("\n    - ") + 2);
        }
        List<String> exampleList = new ArrayList<>();
        while (str.contains("VD")) {
            str = str.substring(str.indexOf("\n        VD") + 1);
            int endPos = str.indexOf("\n");
            String sub = str.substring(str.indexOf("VD"), endPos);
            str = str.substring(endPos);
            exampleList.add(sub);
        }

        wordExamples.setItems(FXCollections.observableArrayList(exampleList));
    }
    @FXML
    private void setWordExample() {
        if (wordExamples.getValue() != null) {
            exampleText.setText(wordExamples.getValue());
        }
    }

    @FXML
    public void addExample() {
        if (wordExamples.getValue() == null) {
            wordDef = TabARURequestDelegator.addSentence(wordDef, wordMeanings.getValue(), exampleText.getText().replace("\n", ""), "\n        VD: ");
        } else {
            wordDef = wordDef.replace(wordExamples.getValue(), exampleText.getText().replace("\n", ""));
            wordExamples.setValue(null);
        }

        exampleText.clear();

        updatePrototypeText();
    }

    @FXML
    public void undo() {
        currentWordDef = currentWordDef.prev;
        if (currentWordDef.prev == null) {
            undoB.setDisable(true);
        }
        redoB.setDisable(false);
        double scrollPosition = prototypeText.getScrollTop();
        prototypeText.setText(currentWordDef.data);
        prototypeText.setScrollTop(scrollPosition);
        wordDef = currentWordDef.data;
    }

    @FXML
    public void redo() {
        currentWordDef = currentWordDef.next;
        if (currentWordDef.next == null) {
            redoB.setDisable(true);
        }
        undoB.setDisable(false);
        double scrollPosition = prototypeText.getScrollTop();
        prototypeText.setText(currentWordDef.data);
        prototypeText.setScrollTop(scrollPosition);
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

    @FXML
    public void exportDB() {
        if (TabARURequestDelegator.exportDB()) {
            prototypeText.setText(" File exported to .../EN-VI-Dictionary/src/main/resources/external_dictionary/Dictionary_EX.txt");
        }
    }

}
