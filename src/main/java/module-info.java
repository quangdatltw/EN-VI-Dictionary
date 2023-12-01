module dictionary.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires jlayer;
    requires java.net.http;


    opens dictionary.gui to javafx.fxml;
    exports dictionary.gui;
    exports dictionary.gui.control;
    opens dictionary.gui.control to javafx.fxml;
    exports dictionary.gui.request;
    opens dictionary.gui.request to javafx.fxml;
}
