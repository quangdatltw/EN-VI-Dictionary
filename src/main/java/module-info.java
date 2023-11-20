module dictionary.gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires jlayer;
    requires java.net.http;

    opens dictionary.gui to javafx.fxml;
    exports dictionary.gui;
}
