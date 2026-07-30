package com.example.rpg_manager;

import com.example.rpg_manager.model.Classes;
import com.example.rpg_manager.model.Personagem;
import com.example.rpg_manager.repository.ClassesRepository;
import com.example.rpg_manager.repository.PersonagemRepository;
import com.example.rpg_manager.services.PersonagemServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PersonagensController implements Initializable {

    private String caminhoAvatar;

    @FXML
    private ComboBox<Classes> classesComboBox;

    @FXML
    private GridPane gridPersonagens;

    @FXML
    private TextField nomeP;

    @FXML
    private Spinner<Integer> nvlS;

    @FXML
    private Button saveBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private ImageView previewAvatar;

    private List<Classes> classes = new ArrayList<>();

    private ClassesRepository classesRepository = new ClassesRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classes.clear();

        classesComboBox.setItems(
                FXCollections.observableArrayList(classesRepository.listar())
        );

        atualizarCards();

        nvlS.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)
        );
    }

    private final PersonagemRepository repository =
            new PersonagemRepository();

    PersonagemServices service = new PersonagemServices();



    @FXML
    private void escolherAvatar() {

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Escolher avatar do personagem");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Imagens",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )
        );

        File file = chooser.showOpenDialog(nomeP.getScene().getWindow());

        if (file != null) {

            caminhoAvatar = file.getAbsolutePath();

            previewAvatar.setImage(
                    new Image(file.toURI().toString())
            );
        }

    }


    @FXML
    public void salvar() {

        if (personagemSelecionado == null) {

            Personagem novo = new Personagem(
                    nomeP.getText(),
                    nvlS.getValue(),
                    classesComboBox.getValue()
            );

            novo.setAvatar(caminhoAvatar);

            service.salvar(novo);

        } else {

            personagemSelecionado.setNome(nomeP.getText());

            personagemSelecionado.setNivel(nvlS.getValue());

            personagemSelecionado.setClasse(classesComboBox.getValue());

            personagemSelecionado.setAvatar(caminhoAvatar);

            service.atualizar(personagemSelecionado);

            personagemSelecionado = null;

        }

        atualizarCards();

        limpar();

    }

    public void limpar() {

        nomeP.clear();
        nvlS.getValueFactory().setValue(1);
        classesComboBox.getSelectionModel().clearSelection();
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
                e.printStackTrace();
            }
        }
    }

//    @FXML
//    public void atualizar() {
//        Personagem selecionado =
//                tabela.getSelectionModel().getSelectedItem();
//
//        if (selecionado == null) return;
//
//        selecionado.setNome(nomeP.getText());
//        selecionado.setNivel(nvlS.getValue());
//        selecionado.setClasse((classesComboBox.getValue()));
//
//        service.atualizar(selecionado);
//
//        atualizarCards();
//
//        limpar();
//    }
//
//    @FXML
//    public void excluir() {
//
//        Personagem selecionado =
//                tabela.getSelectionModel().getSelectedItem();
//
//        if (selecionado == null) return;
//
//        service.excluir(selecionado.getId());
//
//        atualizarCards();
//
//        limpar();
//    }


    private Personagem personagemSelecionado;

    private void editarPersonagem(Personagem personagem) {

        personagemSelecionado = personagem;

        nomeP.setText(personagem.getNome());

        nvlS.getValueFactory().setValue(personagem.getNivel());

        caminhoAvatar = personagem.getAvatar();

        personagemSelecionado.setAvatar(caminhoAvatar);

        classesComboBox.setValue(personagem.getClasse());

    }

    private void excluirPersonagem(Personagem personagem) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Excluir personagem");

        alert.setHeaderText("Deseja realmente excluir "
                + personagem.getNome() + "?");

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

            service.excluir(personagem.getId());

            atualizarCards();

        }

    }

}
