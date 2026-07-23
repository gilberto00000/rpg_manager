package com.example.rpg_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private StackPane centerPane;

    @FXML
    private VBox leftPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane mainPane;

    @FXML
    private ImageView backgroundId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(
                getClass().getResource("images/background.png").toExternalForm()
        );

        backgroundId.setImage(image);

        backgroundId.fitWidthProperty().bind(mainPane.widthProperty());
        backgroundId.fitHeightProperty().bind(mainPane.heightProperty());


        leftPane.prefWidthProperty().bind(borderPane.widthProperty().multiply(0.25));
    }

    @FXML
    private void abrirPersona() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("fxml/telaPersonagens.fxml")
        );

        Parent tela = loader.load();



        centerPane.getChildren().setAll(tela);
    }




}

