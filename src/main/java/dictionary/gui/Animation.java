package dictionary.gui;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.util.Duration;

public class Animation {
    public static void tabAnimation(TabPane tabPane) {

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                animateTabSwitch(tabPane, oldTab, newTab);
            }
        });
}

    private static void animateTabSwitch(TabPane tabPane, Tab oldTab, Tab newTab) {
        int pos = tabPane.getTabs().indexOf(newTab);
        for (Tab tab : tabPane.getTabs()) {
            if (tabPane.getTabs().indexOf(oldTab) > pos) {
                tab.getContent().setTranslateX(-tabPane.getWidth());
            } else {
                tab.getContent().setTranslateX(tabPane.getWidth());
            }
        }
        TranslateTransition transitionIn = new TranslateTransition(Duration.seconds(0.6), newTab.getContent());
        transitionIn.setToX(0);
        transitionIn.play();

    }
}
