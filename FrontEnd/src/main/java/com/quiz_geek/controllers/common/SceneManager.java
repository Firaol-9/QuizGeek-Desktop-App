package com.quiz_geek.controllers.common;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static final Map<String, Parent> cache = new HashMap<>();
    private static final Map<String, Object> controllers = new HashMap<>();

    public static Parent getPage(String fxmlFile) throws IOException {
        if (!cache.containsKey(fxmlFile)){
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource( "/com/quiz_geek/FxmlFiles/" + fxmlFile));
            Parent root = loader.load();
            cache.put(fxmlFile, root);
            controllers.put(fxmlFile, loader.getController());
        }

        return cache.get(fxmlFile);
    }

    public static Object getController(String fxmlFile){
        return controllers.get(fxmlFile);
    }

    public static void deletePage(String fxmlFile){
        cache.remove(fxmlFile);
        controllers.remove(fxmlFile);
    }

    public static void clearAllPage(){
        cache.clear();
        controllers.clear();
    }

    public static void switchPage(Pane container, String fxmlFile) throws IOException {
        Parent newPage = getPage(fxmlFile);

        // If container already has something, fade it out first
        if (!container.getChildren().isEmpty()) {
            Parent oldPage = (Parent) container.getChildren().getFirst();

            FadeTransition fadeOut = new FadeTransition(Duration.millis(250), oldPage);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(event -> {
                container.getChildren().setAll(newPage);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(250), newPage);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        } else {
            container.getChildren().setAll(newPage);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(250), newPage);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }
    }
}
