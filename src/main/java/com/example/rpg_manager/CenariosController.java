package com.example.rpg_manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CenariosController implements Initializable {


    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane mainPane;

    @FXML
    private ImageView backgroundId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(
                getClass().getResource("images/backgroundCenarios.png").toExternalForm()
        );

        backgroundId.setImage(image);

        backgroundId.fitWidthProperty().bind(mainPane.widthProperty());
        backgroundId.fitHeightProperty().bind(mainPane.heightProperty());

    }
}
