package com.example.rpg_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AtributoControlController {

    @FXML
    private Label nomeLabel;

    @FXML
    private Label valorLabel;

    @FXML
    private Button maisBtn;

    @FXML
    private Button menosBtn;

    private int valor = 0;

    private Runnable onAumentar;
    private Runnable onDiminuir;

    @FXML
    private void aumentar() {
        if (onAumentar != null) {
            onAumentar.run();
        }
    }

    @FXML
    private void diminuir() {
        if (onDiminuir != null) {
            onDiminuir.run();
        }
    }

    public void setNome(String nome) {
        nomeLabel.setText(nome);
    }

    public void setValor(int valor) {
        this.valor = valor;
        valorLabel.setText(String.valueOf(valor));
    }

    public int getValor() {
        return valor;
    }

    public void setOnAumentar(Runnable onAumentar) {
        this.onAumentar = onAumentar;
    }

    public void setOnDiminuir(Runnable onDiminuir) {
        this.onDiminuir = onDiminuir;
    }

    public void setMaisDesabilitado(boolean desabilitado) {
        maisBtn.setDisable(desabilitado);
    }

    public void setMenosDesabilitado(boolean desabilitado) {
        menosBtn.setDisable(desabilitado);
    }
}