package com.example.rpg_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("fxml/main.fxml"));

        Image icone = new Image(getClass().getResourceAsStream("icons/icon.png"));

        stage.getIcons().add(icone);
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(
                MainApp.class.getResource("style/main.css").toExternalForm()
        );
        stage.setTitle("OtherSide 1.0");
        stage.setScene(scene);
        stage.show();
    }
}
