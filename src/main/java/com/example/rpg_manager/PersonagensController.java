package com.example.rpg_manager;

import com.example.rpg_manager.model.Classes;
import com.example.rpg_manager.model.Personagem;
import com.example.rpg_manager.repository.ClassesRepository;
import com.example.rpg_manager.repository.PersonagemRepository;
import com.example.rpg_manager.services.PersonagemServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class PersonagensController implements Initializable {

    @FXML
    private ComboBox<Classes> classesComboBox;

    @FXML
    private TextField nomeP;

    @FXML
    private Spinner<Integer> nvlS;

    @FXML
    private Button saveBtn;

    @FXML
    private Button clearBtn;

    private List<Classes> classes = new ArrayList<>();

    private ClassesRepository classesRepository = new ClassesRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            classes.clear();

        classesComboBox.setItems(
                FXCollections.observableArrayList(classesRepository.listar())
        );


            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colClasse.setCellValueFactory(new PropertyValueFactory<>("classe"));
            colNvl.setCellValueFactory(new PropertyValueFactory<>("nivel"));

            atualizarTabela();

            nvlS.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)
            );

    }


    private final PersonagemRepository repository =
            new PersonagemRepository();

    @FXML
    private TableView<Personagem> tabela;

    @FXML
    TableColumn<Personagem, String> colNome;

    @FXML
    TableColumn<Personagem, String> colClasse;

    @FXML
    TableColumn<Personagem, Integer> colNvl;

    PersonagemServices service = new PersonagemServices();

    public void salvar(){

        Personagem p = new Personagem(
                nomeP.getText(),

                nvlS.getValue(),

                classesComboBox.getValue()
        );

        service.salvar(p);

        limpar();
        atualizarTabela();
    }

    public void limpar(){

        nomeP.clear();
        nvlS.getValueFactory().setValue(1);
        classesComboBox.getSelectionModel().clearSelection();
    }

    private void atualizarTabela() {
        tabela.getItems().setAll(service.listar());
    }




}
