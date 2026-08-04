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
import javafx.scene.layout.HBox;
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
    private void novoPersonagem() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/rpg_manager/fxml/FichaPersonagem.fxml"
                    )
            );

            Parent root = loader.load();

            FichaPersonagemController controller = loader.getController();

            controller.setAoSalvar(this::atualizarCards);

            Scene scene = new Scene(root);

            Stage novaJanela = new Stage();

            novaJanela.setTitle("Novo personagem");
            novaJanela.setScene(scene);
            novaJanela.setMinWidth(900);
            novaJanela.setMinHeight(650);

            novaJanela.show();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao abrir ficha do personagem",
                    e
            );
        }

    }

    private void atualizarCards() {
        gridPersonagens.getChildren().clear();

        List<Personagem> todos = service.listar();

        atualizarControlesPaginacao(todos.size());

        int inicio = paginaAtual * itensPorPagina;
        int fim = Math.min(
                inicio + itensPorPagina,
                todos.size()
        );

        List<Personagem> pagina = todos.subList(inicio, fim);

        int coluna = 0;
        int linha = 0;

        for (Personagem personagem : pagina) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "fxml/personagem-card.fxml"
                        )
                );

                VBox card = loader.load();

                PersonagemCardController controller =
                        loader.getController();

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
                throw new RuntimeException(
                        "Erro ao carregar card do personagem",
                        e
                );
            }
        }
    }

    private void editarPersonagem(Personagem personagem) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/rpg_manager/fxml/FichaPersonagem.fxml"
                    )
            );

            Parent root = loader.load();

            FichaPersonagemController controller =
                    loader.getController();

            controller.setPersonagem(personagem);
            controller.setAoSalvar(this::atualizarCards);

            Stage novaJanela = new Stage();

            novaJanela.setTitle(
                    "Ficha — " + personagem.getNome()
            );

            novaJanela.setScene(new Scene(root));
            novaJanela.setMinWidth(900);
            novaJanela.setMinHeight(650);

            novaJanela.show();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao abrir ficha do personagem",
                    e
            );
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

    //paginação

    @FXML
    private Label paginationInfoLabel;

    @FXML
    private Button previousPageButton;

    @FXML
    private Button nextPageButton;

    @FXML
    private HBox pageButtonsContainer;

    private int paginaAtual = 0;
    private final int itensPorPagina = 6;
    private int totalPaginas = 1;

    @FXML
    private void paginaAnterior() {
        if (paginaAtual > 0) {
            paginaAtual--;
            atualizarCards();
        }
    }

    @FXML
    private void proximaPagina() {
        if (paginaAtual < totalPaginas - 1) {
            paginaAtual++;
            atualizarCards();
        }
    }

    private void atualizarControlesPaginacao(int totalPersonagens) {
        totalPaginas = Math.max(
                1,
                (int) Math.ceil((double) totalPersonagens / itensPorPagina)
        );

        if (paginaAtual >= totalPaginas) {
            paginaAtual = totalPaginas - 1;
        }

        previousPageButton.setDisable(paginaAtual == 0);
        nextPageButton.setDisable(paginaAtual >= totalPaginas - 1);

        int inicio = totalPersonagens == 0
                ? 0
                : paginaAtual * itensPorPagina + 1;

        int fim = Math.min(
                (paginaAtual + 1) * itensPorPagina,
                totalPersonagens
        );

        paginationInfoLabel.setText(
                inicio + "–" + fim + " de "
                        + totalPersonagens + " personagens"
        );

        pageButtonsContainer.getChildren().clear();

        for (int i = 0; i < totalPaginas; i++) {
            int indicePagina = i;

            Button pageButton = new Button(
                    String.valueOf(i + 1)
            );

            pageButton.getStyleClass().add(
                    "pagination-page-button"
            );

            if (i == paginaAtual) {
                pageButton.getStyleClass().add(
                        "pagination-page-button-active"
                );
            }

            pageButton.setOnAction(event -> {
                paginaAtual = indicePagina;
                atualizarCards();
            });

            pageButtonsContainer
                    .getChildren()
                    .add(pageButton);
        }
    }


}
