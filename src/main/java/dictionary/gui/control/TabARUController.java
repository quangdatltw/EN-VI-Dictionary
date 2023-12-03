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
    @FXML
    public void initialize() {
        loadWordTypeList();
        reset();
        searchWord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                addWord.setDisable(true);
                updateWord.setDisable(true);
                removeWord.setDisable(true);
                meaning.setDisable(true);
                example.setDisable(true);
                wordType.setDisable(true);
            }
            assert newValue != null;
            searchWord.setText(newValue.toLowerCase());
            checkWord();
        });

    }

    private void loadWordTypeList() {
        wordType.setItems(FXCollections.observableArrayList(wordTypesList));
    }

    private void reset() {
        searchWord.setText("");
        wordDef = "";
        addWord.setDisable(true);
        updateWord.setDisable(true);
        removeWord.setDisable(true);
        meaning.setDisable(true);
        example.setDisable(true);
        wordType.setDisable(true);
    }

    private void concatDef(String add) {
        wordDef = wordDef + "\n" + add;
        prototypeText.setText(wordDef);
    }

    public void checkWord() {
        String word = searchWord.getText().trim();
        if (word.isEmpty()) {
            return;
        }
        if (TabARURequestDelegator.checkWord(word)) {
            ifWordIsExistent(word);
        } else {
            ifWordIsNonexistent(word);
        }
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

    private static String wordDef = "";
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



    private void ifWordIsExistent(String word) {
        setWordDef(word);

        updateWord.setDisable(false);
        removeWord.setDisable(false);
        wordType.setDisable(false);
    }

    private void setWordDef(String word) {
        wordDef = TabARURequestDelegator.lookup(word);
        prototypeText.setText(wordDef);
    }

    private void ifWordIsNonexistent(String word) {
        wordDef = word;
        prototypeText.setText(wordDef);

        addWord.setDisable(false);
        wordType.setDisable(false);
    }

    @FXML
    public void setWordType() {
        String type = wordType.getValue();
        if (type != null && !wordDef.contains(type)) {
            concatDef(type);

            meaning.setDisable(false);
            example.setDisable(false);
        }
    }

    private String cutWordType() {
        String str = wordDef.substring(wordDef.indexOf(wordType.getValue()));

        if (str.contains("\n*")) {
            str = str.substring(0, str.indexOf("\n*"));
        }

        if (str.contains(wordTypesList.get(6))) {
            str = str.substring(0, str.indexOf(wordTypesList.get(6)));
        }
        System.out.println(str);
        return  str;
    }
    @FXML
    public void getWordMeaningList() {
        String str = cutWordType();

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
    public void addMeaning() {
        if (wordDef.contains(meaningText.getText())) {
            return;
        }
        String set = cutWordType();
        System.out.println(set);
        wordDef = wordDef.replace(set, set + "\n    - " + meaningText.getText());
        System.out.println(wordDef);
        prototypeText.setText(wordDef);
    }

    @FXML
    public void addExample() {

    }

    @FXML
    public void undo() {

    }

    @FXML
    public void redo() {

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
    }

}
