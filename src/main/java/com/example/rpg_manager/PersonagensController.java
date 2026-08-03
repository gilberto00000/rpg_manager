package com.example.rpg_manager;

import com.example.rpg_manager.model.Personagem;
import com.example.rpg_manager.services.PersonagemServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PersonagensController implements Initializable {

    @FXML
    private GridPane gridPersonagens;

    private final PersonagemServices service = new PersonagemServices();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizarCards();
    }

    @FXML
    private void novoPersonagem() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("fxml/FichaPersonagem.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) gridPersonagens.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void atualizarCards() {
        gridPersonagens.getChildren().clear();

        int coluna = 0;
        int linha = 0;

        for (Personagem personagem : service.listar()) {

            try {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("fxml/personagem-card.fxml")
                );

                VBox card = loader.load();

                PersonagemCardController controller = loader.getController();

                controller.setPersonagem(personagem);

                controller.setOnEditar(this::editarPersonagem);

                controller.setOnExcluir(this::excluirPersonagem);

                gridPersonagens.add(card, coluna, linha);

                coluna++;

                if (coluna == 3) {
                    coluna = 0;
                    linha++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void editarPersonagem(Personagem personagem) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "fxml/FichaPersonagem.fxml"
                    )
            );

            Parent root = loader.load();

            FichaPersonagemController controller =
                    loader.getController();

            controller.setPersonagem(personagem);

            Stage stage =
                    (Stage) gridPersonagens.getScene().getWindow();

            stage.setScene(new Scene(root));

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    private void excluirPersonagem(Personagem personagem) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Excluir personagem");

        alert.setHeaderText(
                "Deseja realmente excluir " + personagem.getNome() + "?"
        );

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

            service.excluir(personagem.getId());

            atualizarCards();

        }
    }
}
