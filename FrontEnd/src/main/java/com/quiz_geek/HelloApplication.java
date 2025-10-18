package com.quiz_geek;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/quiz_geek/FxmlFiles/common/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("QuizGeek");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/quiz_geek/images/appIcon.png")));
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}