package dictionary.gui;

import dictionary.db.WordHistory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    private static Stage stg;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Library_Type.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setTitle("LIBRARY'S TYPE");
        Image icon = new Image("App_icon.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
        stg = primaryStage;
    }

    /**
     * Gets second stage.
     *
     * @return the second stage
     * @throws IOException the io exception
     */
    public static Stage getSecondStage() throws IOException {
        stg.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("fxml/App.fxml")));
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("EN-VI Dictionary");
        Scene app = new Scene(root);
        secondaryStage.getIcons().add(new Image("App_icon.png"));
        secondaryStage.setScene(app);
        secondaryStage.setMinWidth(400);
        secondaryStage.setMinHeight(350);
        secondaryStage.centerOnScreen();
        secondaryStage.setOnCloseRequest(event -> WordHistory.exportToFile());
        stg = secondaryStage;
        return secondaryStage;
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch();
    }
}

