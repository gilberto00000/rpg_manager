package com.example.rpg_manager;

import com.example.rpg_manager.model.Item;
import com.example.rpg_manager.services.ItemService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ItensController implements Initializable {

    @FXML
    private GridPane gridItens;

    @FXML
    private Label paginationInfoLabel;

    @FXML
    private Button previousPageButton;

    @FXML
    private Button nextPageButton;

    @FXML
    private HBox pageButtonsContainer;

    private final ItemService service = new ItemService();

    private int paginaAtual = 0;

    private final int itensPorPagina = 6;

    private int totalPaginas = 1;


    @Override
    public void initialize(
            URL location,
            ResourceBundle resources
    ) {
        atualizarCards();
    }


    /*
     * ==================================================
     * CRIAR ITEM
     * ==================================================
     */

    @FXML
    private void novoItem() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/rpg_manager/fxml/FichaItem.fxml"
                    )
            );

            Parent root = loader.load();

            FichaItemController controller =
                    loader.getController();

            /*
             * Quando salvar o item,
             * atualiza a tela principal automaticamente.
             */
            controller.setAoSalvar(
                    this::atualizarCards
            );

            Stage janela = new Stage();

            janela.setTitle("Novo item");

            janela.setScene(
                    new Scene(root)
            );

            janela.setMinWidth(700);
            janela.setMinHeight(550);

            janela.show();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao abrir ficha do item",
                    e
            );
        }
    }


    /*
     * ==================================================
     * CARREGAR CARDS
     * ==================================================
     */

    private void atualizarCards() {

        gridItens
                .getChildren()
                .clear();

        List<Item> todos =
                service.listar();

        atualizarControlesPaginacao(
                todos.size()
        );

        int inicio =
                paginaAtual * itensPorPagina;

        int fim = Math.min(
                inicio + itensPorPagina,
                todos.size()
        );

        if (inicio > fim) {
            return;
        }

        List<Item> itensDaPagina =
                todos.subList(
                        inicio,
                        fim
                );

        int coluna = 0;
        int linha = 0;

        for (Item item : itensDaPagina) {

            try {

                FXMLLoader loader =
                        new FXMLLoader(
                                getClass().getResource(
                                        "/com/example/rpg_manager/fxml/item-card.fxml"
                                )
                        );

                VBox card =
                        loader.load();

                ItemCardController controller =
                        loader.getController();

                controller.setItem(item);

                controller.setOnEditar(
                        this::editarItem
                );

                controller.setOnExcluir(
                        this::excluirItem
                );

                gridItens.add(
                        card,
                        coluna,
                        linha
                );

                coluna++;

                if (coluna == 3) {

                    coluna = 0;

                    linha++;
                }

            } catch (IOException e) {

                throw new RuntimeException(
                        "Erro ao carregar card do item "
                                + item.getNome(),
                        e
                );
            }
        }
    }


    /*
     * ==================================================
     * EDITAR ITEM
     * ==================================================
     */

    private void editarItem(Item item) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/com/example/rpg_manager/fxml/FichaItem.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            FichaItemController controller =
                    loader.getController();

            controller.setItem(item);

            controller.setAoSalvar(
                    this::atualizarCards
            );

            Stage janela =
                    new Stage();

            janela.setTitle(
                    "Editar item — "
                            + item.getNome()
            );

            janela.setScene(
                    new Scene(root)
            );

            janela.setMinWidth(700);
            janela.setMinHeight(550);

            janela.show();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao abrir item para edição",
                    e
            );
        }
    }


    /*
     * ==================================================
     * EXCLUIR ITEM
     * ==================================================
     */

    private void excluirItem(Item item) {

        boolean confirmou =
                com.example.rpg_manager.utils.ConfirmDialog.show(
                        gridItens
                                .getScene()
                                .getWindow(),

                        "Excluir item",

                        "Deseja realmente excluir "
                                + item.getNome()
                                + "?",

                        "Excluir"
                );

        if (!confirmou) {
            return;
        }

        service.excluir(
                item.getId()
        );

        /*
         * Se excluiu o último item de uma página,
         * pode ser necessário voltar uma página.
         */
        List<Item> restantes =
                service.listar();

        int novasPaginas =
                Math.max(
                        1,
                        (int) Math.ceil(
                                (double) restantes.size()
                                        / itensPorPagina
                        )
                );

        if (paginaAtual >= novasPaginas) {
            paginaAtual =
                    novasPaginas - 1;
        }

        atualizarCards();
    }


    /*
     * ==================================================
     * PAGINAÇÃO
     * ==================================================
     */

    @FXML
    private void paginaAnterior() {

        if (paginaAtual <= 0) {
            return;
        }

        paginaAtual--;

        atualizarCards();
    }


    @FXML
    private void proximaPagina() {

        if (
                paginaAtual
                        >= totalPaginas - 1
        ) {
            return;
        }

        paginaAtual++;

        atualizarCards();
    }


    private void atualizarControlesPaginacao(
            int totalItens
    ) {

        totalPaginas =
                Math.max(
                        1,
                        (int) Math.ceil(
                                (double) totalItens
                                        / itensPorPagina
                        )
                );

        if (
                paginaAtual
                        >= totalPaginas
        ) {
            paginaAtual =
                    totalPaginas - 1;
        }

        previousPageButton.setDisable(
                paginaAtual == 0
        );

        nextPageButton.setDisable(
                paginaAtual
                        >= totalPaginas - 1
        );


        /*
         * Exemplo:
         * 1–6 de 15 itens
         */

        int inicio =
                totalItens == 0
                        ? 0
                        : paginaAtual
                          * itensPorPagina
                          + 1;

        int fim =
                Math.min(
                        (paginaAtual + 1)
                                * itensPorPagina,
                        totalItens
                );

        paginationInfoLabel.setText(
                inicio
                        + "–"
                        + fim
                        + " de "
                        + totalItens
                        + " itens"
        );


        /*
         * Botões:
         *
         * [1] [2] [3]
         */

        pageButtonsContainer
                .getChildren()
                .clear();

        for (
                int i = 0;
                i < totalPaginas;
                i++
        ) {

            int indicePagina = i;

            Button pageButton =
                    new Button(
                            String.valueOf(
                                    i + 1
                            )
                    );

            pageButton
                    .getStyleClass()
                    .add(
                            "pagination-page-button"
                    );

            if (
                    i == paginaAtual
            ) {

                pageButton
                        .getStyleClass()
                        .add(
                                "pagination-page-button-active"
                        );
            }

            pageButton.setOnAction(
                    event -> {

                        paginaAtual =
                                indicePagina;

                        atualizarCards();
                    }
            );

            pageButtonsContainer
                    .getChildren()
                    .add(
                            pageButton
                    );
        }
    }
}