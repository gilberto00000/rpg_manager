package com.example.rpg_manager;

import com.example.rpg_manager.model.Personagem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.function.Consumer;

public class PersonagemCardController {


    @FXML
    private Label nomeLabel;

    @FXML
    private Label classeLabel;

    @FXML
    private Label nexLabel;

    @FXML
    private Button editarBtn;

    @FXML
    private Button excluirBtn;

    private Personagem personagem;

    private Consumer<Personagem> onEditar;
    private Consumer<Personagem> onExcluir;

    @FXML
    private ImageView avatarImage;

    public void setPersonagem(Personagem personagem) {

        this.personagem = personagem;

        nomeLabel.setText(personagem.getNome());

        if (personagem.getClasse() != null) {
            classeLabel.setText(personagem.getClasse().getNome());
        } else {
            classeLabel.setText("Sem classe");
        }

        nexLabel.setText("NEX " + personagem.getNex() + "%");

        if (personagem.getAvatar() == null || personagem.getAvatar().isBlank()) {

            avatarImage.setImage(new Image(
                    getClass().getResourceAsStream(
                            "/com/example/rpg_manager/images/default-avatar.png"
                    )
            ));

        } else {

            avatarImage.setImage(new Image(
                    new File(personagem.getAvatar()).toURI().toString()
            ));

        }

    }



    public void setOnEditar(Consumer<Personagem> onEditar) {
        this.onEditar = onEditar;
    }

    public void setOnExcluir(Consumer<Personagem> onExcluir) {
        this.onExcluir = onExcluir;
    }

    @FXML
    private void editar() {
        if (onEditar != null) {
            onEditar.accept(personagem);
        }
    }

    @FXML
    private void excluir() {
        if (onExcluir != null) {
            onExcluir.accept(personagem);
        }
    }
}

