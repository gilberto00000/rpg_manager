package com.example.rpg_manager;

import com.example.rpg_manager.model.Habilidade;
import com.example.rpg_manager.model.Personagem;
import com.example.rpg_manager.services.HabilidadesService;
import com.example.rpg_manager.utils.ConfirmDialog;
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

public class HabilidadesController implements Initializable {

    @FXML
    private GridPane gridHabilidades;
    @FXML
    private Label paginationInfoLabel;
    @FXML
    private Button previousPageButton;
    @FXML
    private HBox pageButtonsContainer;
    @FXML
    private Button nextPageButton;

    private final HabilidadesService service = new HabilidadesService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void novaHabilidade() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "fxml/FichaHabilidades.fxml"
                    )
            );

            Parent root = loader.load();

            FichaHabilidadesController controller = loader.getController();

            controller.setAoSalvar(this::atualizarCards);

            Scene scene = new Scene(root);

            Stage novaJanela = new Stage();

            novaJanela.setTitle("Nova habilidade");
            novaJanela.setScene(scene);
            novaJanela.setMinWidth(900);
            novaJanela.setMinHeight(650);

            novaJanela.show();

        }catch (IOException e){
            throw new RuntimeException(
                    "Não foi possivel abrir a ficha", e
            );
        }
    }

    private void atualizarCards(){
        gridHabilidades.getChildren().clear();

        List<Habilidade> todos = service.listar();

        atualizarControlesPaginacao(todos.size());

        int inicio = paginaAtual * itensPorPagina;
        int fim = Math.min(
                inicio + itensPorPagina,
                todos.size()
        );

        List<Habilidade> pagina = todos.subList(inicio, fim);

        int coluna = 0;
        int linha = 0;

        for (Habilidade habilidade : pagina) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "fxml/habilidades-card.fxml"
                        )
                );

                VBox card = loader.load();

                HabilidadesCardController controller =
                        loader.getController();

                controller.setHabilidade(habilidade);
                controller.setOnEditar(this::editarHabilidade);
                controller.setOnExcluir(this::excluirHabilidade);

                gridHabilidades.add(card, coluna, linha);

                coluna++;

                if (coluna == 4){
                    coluna = 0;
                    linha ++;
                }
            }catch (IOException e) {
                throw new RuntimeException(
                        "Erro ao carregar o card da habilidade", e
                );
            }
        }
    }

    private void editarHabilidade(Habilidade habilidade){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/rpg_manager/fxml/FichaHabilidades.fxml"
                    )
            );

            Parent root = loader.load();

            FichaHabilidadesController controller =
                    loader.getController();
            controller.setHabilidade(habilidade);
            controller.setAoSalvar(this::atualizarCards);

            Stage novaJanela = new Stage();

            novaJanela.setTitle(
                    "Ficha — " + habilidade.getNome()
            );

            novaJanela.setScene(new Scene(root));
            novaJanela.setMinWidth(900);
            novaJanela.setMinHeight(650);

            novaJanela.show();

        } catch (IOException e){
            throw new RuntimeException(
                    "Erro ao abrir as informações da habilidade",
                    e
            );
        }
    }

    private void excluirHabilidade(Habilidade habilidade){

        boolean confirmou = ConfirmDialog.show(
                gridHabilidades.getScene().getWindow(),
                "Excluir habilidade",
                "Deseja realmente excluir "
                    + habilidade.getNome()
                    + "?",
                    "Excluir"
        );

        if (!confirmou){
            return;
        }

        service.excluir(habilidade.getId());

        atualizarCards();
    }


    //paginação

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

    private void atualizarControlesPaginacao(int totalHabilidades) {
        totalPaginas = Math.max(
                1,
                (int) Math.ceil((double) totalHabilidades / itensPorPagina)
        );

        if (paginaAtual >= totalPaginas) {
            paginaAtual = totalPaginas - 1;
        }

        previousPageButton.setDisable(paginaAtual == 0);
        nextPageButton.setDisable(paginaAtual >= totalPaginas - 1);

        int inicio = totalHabilidades == 0
                ? 0
                : paginaAtual * itensPorPagina + 1;

        int fim = Math.min(
                (paginaAtual + 1) * itensPorPagina,
                totalHabilidades
        );

        paginationInfoLabel.setText(
                inicio + "–" + fim + " de "
                        + totalHabilidades + " habilidades"
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
