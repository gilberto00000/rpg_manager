package com.example.rpg_manager;

import java.util.Random;
import com.example.rpg_manager.model.Classes;
import com.example.rpg_manager.model.Personagem;
import com.example.rpg_manager.repository.PersonagemRepository;
import com.example.rpg_manager.services.PersonagemServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.*;
public class DiceRollController implements Initializable {


    @FXML
    private Circle d2;

    @FXML
    private Label resultRolar;

    @FXML
    private void rolarDado(ActionEvent event){

        Button botaoClicado = (Button) event.getSource();

        int lados = Integer.parseInt(botaoClicado.getId());

        int resultado = random.nextInt(lados) + 1;

        System.out.println(resultado);
        resultRolar.setText(""+resultado);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private Random random = new Random();
}

