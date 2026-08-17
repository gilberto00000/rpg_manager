package com.example.rpg_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class DiceRollController implements Initializable {

    @FXML
    private Label resultRolar;

    @FXML
    private Label detalhesRolagem;

    @FXML
    private Label modificadorLabel;

    private final Random random = new Random();

    private final List<Integer> resultados =
            new ArrayList<>();

    private int somaDados = 0;

    private int modificador = 0;


    @Override
    public void initialize(
            URL location,
            ResourceBundle resources
    ) {

        atualizarInterface();
    }


    /*
     * =====================================================
     * ROLAR AO CLICAR NO DADO
     * =====================================================
     */

    @FXML
    private void rolarDado(
            ActionEvent event
    ) {

        Button botao =
                (Button) event.getSource();

        int lados =
                Integer.parseInt(
                        botao.getId()
                );

        int resultado =
                random.nextInt(lados) + 1;

        resultados.add(resultado);

        somaDados += resultado;

        adicionarDetalhe(
                lados,
                resultado
        );

        atualizarInterface();
    }


    /*
     * =====================================================
     * MODIFICADOR +
     * =====================================================
     */

    @FXML
    private void aumentarModificador() {

        modificador++;

        atualizarInterface();
    }


    /*
     * =====================================================
     * MODIFICADOR -
     * =====================================================
     */

    @FXML
    private void diminuirModificador() {

        modificador--;

        atualizarInterface();
    }


    /*
     * =====================================================
     * RESET
     * =====================================================
     */

    @FXML
    private void limparRolagem() {

        resultados.clear();

        somaDados = 0;

        modificador = 0;

        detalhesRolagem.setText("");

        atualizarInterface();
    }


    /*
     * =====================================================
     * INTERFACE
     * =====================================================
     */

    private void atualizarInterface() {

        int total =
                somaDados + modificador;

        resultRolar.setText(
                String.valueOf(total)
        );

        if (modificador > 0) {

            modificadorLabel.setText(
                    "+" + modificador
            );

        } else {

            modificadorLabel.setText(
                    String.valueOf(modificador)
            );
        }
    }


    private void adicionarDetalhe(
            int lados,
            int resultado
    ) {

        String atual =
                detalhesRolagem.getText();

        String novaLinha =
                "d" + lados
                        + " → "
                        + resultado;

        if (atual == null
                || atual.isBlank()) {

            detalhesRolagem.setText(
                    novaLinha
            );

        } else {

            detalhesRolagem.setText(
                    atual
                            + "\n"
                            + novaLinha
            );
        }
    }
}