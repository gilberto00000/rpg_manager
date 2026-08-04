package com.example.rpg_manager;

import com.example.rpg_manager.database.ConnectionFactory;
import com.example.rpg_manager.database.IniciarDataBase;
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

import static java.util.Arrays.setAll;

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


    public ConnectionFactory connect = new ConnectionFactory();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


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

    @FXML
    private void abrirDiceRoll() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("fxml/rolarDados.fxml")
        );

        Parent tela = loader.load();

        centerPane.getChildren().setAll(tela);
    }

    @FXML
    private void abrirCenarios() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("fxml/cenarios.fxml")
        );

        Parent tela = loader.load();

        centerPane.getChildren().setAll(tela);
    }




}

