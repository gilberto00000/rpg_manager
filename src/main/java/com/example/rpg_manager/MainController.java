package com.example.rpg_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane centerPane;

    @FXML
    private void abrirPersona() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("fxml/telaPersonagens.fxml")
        );

        Parent tela = loader.load();

        centerPane.getChildren().setAll(tela);
    }

}

