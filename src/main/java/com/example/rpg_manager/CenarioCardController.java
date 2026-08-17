package com.example.rpg_manager;

import com.example.rpg_manager.model.Cenario;
import com.example.rpg_manager.utils.ImageStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.function.Consumer;

public class CenarioCardController {

    @FXML
    private ImageView cenarioImage;

    @FXML
    private Label nomeLabel;

    private Cenario cenario;

    private Consumer<Cenario> onExibir;
    private Consumer<Cenario> onEditar;
    private Consumer<Cenario> onExcluir;


    public void setCenario(Cenario cenario) {

        this.cenario = cenario;

        nomeLabel.setText(
                cenario.getNome()
        );

        carregarImagem();
    }


    private void carregarImagem() {

        File arquivo =
                ImageStorage.carregarArquivo(
                        cenario.getImagem()
                );

        if (arquivo == null
                || !arquivo.exists()) {

            cenarioImage.setImage(null);
            return;
        }

        cenarioImage.setImage(
                new Image(
                        arquivo
                                .toURI()
                                .toString()
                )
        );
    }


    public void setOnExibir(
            Consumer<Cenario> onExibir
    ) {
        this.onExibir = onExibir;
    }


    public void setOnEditar(
            Consumer<Cenario> onEditar
    ) {
        this.onEditar = onEditar;
    }


    public void setOnExcluir(
            Consumer<Cenario> onExcluir
    ) {
        this.onExcluir = onExcluir;
    }


    @FXML
    private void exibir() {

        if (onExibir != null) {
            onExibir.accept(cenario);
        }
    }


    @FXML
    private void editar() {

        if (onEditar != null) {
            onEditar.accept(cenario);
        }
    }


    @FXML
    private void excluir() {

        if (onExcluir != null) {
            onExcluir.accept(cenario);
        }
    }
}