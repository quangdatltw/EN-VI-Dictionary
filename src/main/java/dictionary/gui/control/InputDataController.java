package dictionary.gui.control;

import dictionary.gui.request.InterfaceRequestDelegate;
import dictionary.db.WordHistory;
import dictionary.gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InputDataController {

    @FXML
    private Label errorText;
    @FXML
    private TextField filePath;
    @FXML
    private Button internalDB;
    @FXML
    private Button externalDB;

    /**
     * Initialize.
     */
    public void initialize() {
        WordHistory.loadFromFile();
    }

    /**
     * Release focusing text-field when click on root pane.
     */
    @FXML
    public void clickRootPane() {
            filePath.getParent().requestFocus();
    }

    /**
     * Import internal database.
     */
    @FXML
    public void importInternalDB() {
        internalDB.setDisable(true);
        InterfaceRequestDelegate.insertDictionaryFromDatabase();
    }

    /**
     * Import external database.
     */
    @FXML
    public void importExternalDB() {
        if (!InterfaceRequestDelegate.insertDictionaryFromFile(filePath.getText())) {
            errorText.setText("File path is incorrect");
        } else {
            externalDB.setDisable(true);
        }

    }


    /**
     * Switch to main app.
     */
    public static void switchToApp() {
        App.getStg().close();

        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("fxml/App.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("EN-VI Dictionary");
        Scene app = new Scene(root);
        secondaryStage.getIcons().add(new Image("App_icon.png"));
        secondaryStage.setScene(app);
        secondaryStage.setMinWidth(400);
        secondaryStage.setMinHeight(350);
        secondaryStage.centerOnScreen();
        secondaryStage.show();
        secondaryStage.setOnCloseRequest(event -> WordHistory.exportToFile());
    }

}