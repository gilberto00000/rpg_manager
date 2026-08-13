package com.example.rpg_manager;

import com.example.rpg_manager.model.Habilidade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class HabilidadesCardController {

    @FXML
    private Label nomeLabel;

    @FXML
    private ImageView avatarImage;

    @FXML
    private Button editarBtn;

    @FXML
    private Button excluirBtn;

    private Habilidade habilidade;

    private Consumer<Habilidade> onEditar;
    private Consumer<Habilidade> onExcluir;
    private Consumer<Habilidade> onRemover;

    public void setHabilidade(Habilidade habilidade) {
        this.habilidade = habilidade;

        nomeLabel.setText(habilidade.getNome());

        if (habilidade.getAvatar() == null || habilidade.getAvatar().isBlank()) {

            avatarImage.setImage(new Image(
                    getClass().getResourceAsStream(
                            "/com/example/rpg_manager/images/default-avatar.png"
                    )
            ));

        } else {

            avatarImage.setImage(new Image(
                    new File(
                            habilidade.getAvatar()
                    ).toURI().toString()
            ));
        }
    }

    public void setOnEditar(Consumer<Habilidade> onEditar) {
        this.onEditar = onEditar;
    }

    public void setOnExcluir(Consumer<Habilidade> onExcluir) {
        this.onExcluir = onExcluir;
    }

    public void setOnRemover(Consumer<Habilidade> onRemover) {
        this.onRemover = onRemover;

        excluirBtn.setText("Remover");
    }

    @FXML
    private void editar() throws IOException {
        if (onEditar != null) {
            onEditar.accept(habilidade);
        }
    }

    @FXML
    private void excluir() throws IOException {

        if (onRemover != null) {
            onRemover.accept(habilidade);
            return;
        }

        if (onExcluir != null) {
            onExcluir.accept(habilidade);
        }
    }
}