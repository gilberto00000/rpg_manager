package com.example.rpg_manager;

import com.example.rpg_manager.model.Pericia;
import com.example.rpg_manager.model.Personagem;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class PericiaControlController {

    @FXML
    private Label nomeLabel;

    @FXML
    private Label atributoLabel;

    @FXML
    private CheckBox treinadoCheckBox;

    @FXML
    private Label bonusLabel;

    @FXML
    private Label totalLabel;

    private Pericia pericia;
    private Personagem personagem;
    private Runnable aoAlterar;

    public void configurar(
            Pericia pericia,
            Personagem personagem,
            Runnable aoAlterar
    ) {
        this.pericia = pericia;
        this.personagem = personagem;
        this.aoAlterar = aoAlterar;

        renderizar();
    }

    @FXML
    private void alterarTreinamento() {
        if (pericia == null) {
            return;
        }

        pericia.setTreinado(
                treinadoCheckBox.isSelected()
        );

        notificarAlteracao();
    }

    @FXML
    private void diminuirBonus() {
        if (pericia == null) {
            return;
        }

        int bonusAtual = pericia.getBonusManual();

        if (bonusAtual > -20) {
            pericia.setBonusManual(bonusAtual - 1);
            notificarAlteracao();
        }
    }

    @FXML
    private void aumentarBonus() {
        if (pericia == null) {
            return;
        }

        int bonusAtual = pericia.getBonusManual();

        if (bonusAtual < 20) {
            pericia.setBonusManual(bonusAtual + 1);
            notificarAlteracao();
        }
    }

    private void notificarAlteracao() {
        renderizar();

        if (aoAlterar != null) {
            aoAlterar.run();
        }
    }

    private void renderizar() {
        if (pericia == null || personagem == null) {
            return;
        }

        nomeLabel.setText(
                pericia.getTipo().toString()
        );

        atributoLabel.setText(
                pericia.getTipo()
                        .getAtributoBase()
                        .toString()
        );

        treinadoCheckBox.setSelected(
                pericia.isTreinado()
        );

        bonusLabel.setText(
                formatarNumero(pericia.getBonusManual())
        );

        totalLabel.setText(
                formatarNumero(
                        pericia.calcularBonus(personagem)
                )
        );
    }

    private String formatarNumero(int valor) {
        return valor >= 0
                ? "+" + valor
                : String.valueOf(valor);
    }
}