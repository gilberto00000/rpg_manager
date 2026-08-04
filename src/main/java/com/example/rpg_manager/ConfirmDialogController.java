package com.example.rpg_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmDialogController {

    @FXML
    private Label tituloLabel;

    @FXML
    private Label mensagemLabel;

    @FXML
    private Button confirmarBtn;

    private boolean confirmado;

    public void configurar(
            String titulo,
            String mensagem,
            String textoConfirmar
    ) {
        tituloLabel.setText(titulo);
        mensagemLabel.setText(mensagem);
        confirmarBtn.setText(textoConfirmar);
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    @FXML
    private void confirmar() {
        confirmado = true;
        fechar();
    }

    @FXML
    private void cancelar() {
        confirmado = false;
        fechar();
    }

    private void fechar() {
        Stage stage = (Stage) confirmarBtn
                .getScene()
                .getWindow();

        stage.close();
    }
}