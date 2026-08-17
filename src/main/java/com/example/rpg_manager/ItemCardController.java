package com.example.rpg_manager;

import com.example.rpg_manager.model.Habilidade;
import com.example.rpg_manager.model.Item;
import com.example.rpg_manager.utils.ImageStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ItemCardController {

    @FXML
    private Label nomeLabel;

    @FXML
    private ImageView avatarImage;

    @FXML
    private Button editarBtn;

    @FXML
    private Button excluirBtn;

    private Item item;

    private Consumer<Item> onEditar;
    private Consumer<Item> onExcluir;
    private Consumer<Item> onRemover;

    public void setItem(Item item) {
        this.item = item;

        nomeLabel.setText(item.getNome());

        carregarAvatar();
    }

    public void setOnEditar(Consumer<Item> onEditar) {
        this.onEditar = onEditar;
    }

    public void setOnExcluir(Consumer<Item> onExcluir) {
        this.onExcluir = onExcluir;
    }

    public void setOnRemover(Consumer<Item> onRemover) {
        this.onRemover = onRemover;

        excluirBtn.setText("Remover");
    }

    private void carregarAvatar() {

        File arquivo = ImageStorage.carregarArquivo(
                item.getAvatar()
        );

        if (arquivo == null || !arquivo.exists()) {

            avatarImage.setImage(
                    new Image(
                            getClass().getResourceAsStream(
                                    "/com/example/rpg_manager/images/default-item.png"
                            )
                    )
            );

            return;
        }

        avatarImage.setImage(
                new Image(
                        arquivo.toURI().toString()
                )
        );
    }

    @FXML
    private void editar() throws IOException {
        if (onEditar != null) {
            onEditar.accept(item);
        }
    }

    @FXML
    private void excluir() throws IOException {

        if (onRemover != null) {
            onRemover.accept(item);
            return;
        }

        if (onExcluir != null) {
            onExcluir.accept(item);
        }
    }
}