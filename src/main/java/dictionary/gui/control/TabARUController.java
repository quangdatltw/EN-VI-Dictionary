package dictionary.gui.control;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;

public class TabARUController {
    @FXML
    public void initialize() {
    }

    private static final List<String> wordTypes = Arrays.asList(
            "* Danh từ:",
            "* Động từ:",
            "* Tính từ:",
            "* Trạng từ:",
            "* Giới từ:",
            "* Phó từ:",
            "! Thành ngữ:",
            "* Nội động từ:",
            "* Ngoại động từ:"
    );
    @FXML
    private TextField word;
    @FXML
    private TextArea prototypeText;
    @FXML
    private TextArea meaningText;
    @FXML
    private TextArea exampleText;

    @FXML
    public void getWordType() {

    }

    @FXML
    public void openWordTypeList() {

    }

    @FXML
    public void checkWord() {

    }

    @FXML
    public void addWord() {

    }

    @FXML
    public void updateWord() {

    }

    @FXML
    public void removeWord() {

    }

    @FXML
    public void addMeaning() {

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
}
