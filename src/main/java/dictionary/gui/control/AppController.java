package dictionary.gui.control;

import dictionary.gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class AppController {
    @FXML
    Tab Tab_Find;
    @FXML
    Tab Tab_GoogleTranslate;
    @FXML
    Tab Tab_AddRemoveUpdate;
    @FXML
    Tab Tab_Game;

    public void initialize() {
        try {
            loadTab(Tab_Find, "fxml/Tab_Find.fxml");
            loadTab(Tab_GoogleTranslate, "fxml/Tab_GoogleTranslate.fxml");
            loadTab(Tab_AddRemoveUpdate, "fxml/Tab_AddRemoveUpdate.fxml");
            loadTab(Tab_Game, "fxml/Tab_Game.fxml");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void loadTab(Tab tab, String fxml) throws IOException {
        AnchorPane tabAnchor = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxml)));
        tab.setContent(tabAnchor);
    }

}