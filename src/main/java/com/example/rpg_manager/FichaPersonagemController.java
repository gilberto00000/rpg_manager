package com.example.rpg_manager;

import com.example.rpg_manager.model.Atributo;
import com.example.rpg_manager.model.Classes;
import com.example.rpg_manager.model.Personagem;
import com.example.rpg_manager.repository.ClassesRepository;
import com.example.rpg_manager.services.PersonagemServices;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;


public class FichaPersonagemController implements Initializable {

    @FXML
    private ImageView avatarPreview;
    @FXML
    private TextField nomeField;
    @FXML
    private ComboBox<Classes> classeCombo;
    @FXML
    private Spinner<Integer> nexSpinner;
    private String caminhoAvatar;
    @FXML
    private Button salvarBtn;
    @FXML
    private VBox atributosContainer;
    @FXML
    private TextField pontosDisponiveisField;
    @FXML
    private Label pontosRestantesLabel;
    @FXML
    private Label vidaLabel;
    @FXML
    private Label peLabel;
    @FXML
    private Label sanidadeLabel;
    @FXML
    private Label defesaLabel;
    @FXML
    private Label esquivaLabel;
    @FXML
    private Label bloqueioLabel;
    @FXML
    private Label deslocamentoLabel;
    @FXML
    private Label limitePeTurnoLabel;

    private final Map<Atributo, AtributoControlController> controlesAtributos =
            new EnumMap<>(Atributo.class);


    @FXML
    private void voltar() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("fxml/main.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) salvarBtn.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.show();
    }

    private PersonagemServices service = new PersonagemServices();
    private Personagem personagemAtual = new Personagem();
    private ClassesRepository classesRepository = new ClassesRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classeCombo.setItems(
                FXCollections.observableArrayList(classesRepository.listar())
        );

        initializeAtributos();

        nexSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)
        );

        avatarPreview.setImage(
                new Image(
                        getClass().getResourceAsStream(
                                "/com/example/rpg_manager/images/default-avatar.png"
                        )
                )
        );

        personagemAtual = new Personagem();



        renderizarFicha();
    }

    public void setPersonagem(Personagem personagem) {

        this.personagemAtual = personagem;

        preencherCampos();

    }

    private void preencherCampos() {

        nomeField.setText(personagemAtual.getNome());

        classeCombo.setValue(personagemAtual.getClasse());

        nexSpinner.getValueFactory().setValue(personagemAtual.getNex());

        pontosDisponiveisField.setText(
                String.valueOf(personagemAtual.getPontosDisponiveis())
        );

        renderizarFicha();
    }

    @FXML
    private void escolherAvatar() {

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Escolher avatar");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Imagens",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )
        );

        File file = chooser.showOpenDialog(
                nomeField.getScene().getWindow()
        );

        if (file != null) {

            caminhoAvatar = file.getAbsolutePath();

            avatarPreview.setImage(
                    new Image(file.toURI().toString())
            );

        }

    }

    private void montarPersonagem() {

        if (personagemAtual == null) {
            personagemAtual = new Personagem();
        }

        personagemAtual.setNome(nomeField.getText());
        personagemAtual.setClasse(classeCombo.getValue());
        personagemAtual.setNex(nexSpinner.getValue());

        personagemAtual.setPontosDisponiveis(
                Integer.parseInt(pontosDisponiveisField.getText())
        );

        personagemAtual.setAgilidade(
                controlesAtributos.get(Atributo.AGILIDADE).getValor()
        );

        personagemAtual.setForca(
                controlesAtributos.get(Atributo.FORCA).getValor()
        );

        personagemAtual.setIntelecto(
                controlesAtributos.get(Atributo.INTELECTO).getValor()
        );

        personagemAtual.setPresenca(
                controlesAtributos.get(Atributo.PRESENCA).getValor()
        );

        personagemAtual.setVigor(
                controlesAtributos.get(Atributo.VIGOR).getValor()
        );

    }

    public void carregarPersonagem(Personagem personagem) {

        this.personagemAtual = personagem;

        nomeField.setText(personagem.getNome());

        classeCombo.setValue(personagem.getClasse());

        nexSpinner.getValueFactory().setValue(personagem.getNex());

        caminhoAvatar = personagem.getAvatar();

        if (caminhoAvatar != null) {

            avatarPreview.setImage(
                    new Image(new File(caminhoAvatar).toURI().toString())
            );

        }
    }

    @FXML
    private void salvar() throws IOException {

        montarPersonagem();

        if (personagemAtual.getId() == null) {

            service.salvar(personagemAtual);

        } else {

            service.atualizar(personagemAtual);

        }

        System.out.println(personagemAtual.getId());

        voltar();

    }

    private void initializeAtributos() {

        pontosDisponiveisField.setText(
                String.valueOf(personagemAtual.getPontosDisponiveis())
        );

        atributosContainer.getChildren().clear();

        for (Atributo atributo : Atributo.values()) {

            try {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "fxml/AtributoControl.fxml"
                        )
                );

                HBox control = loader.load();

                AtributoControlController controller =
                        loader.getController();

                controller.setNome(atributo.getNome());

                controller.setValor(
                        personagemAtual.getValorAtributo(atributo)
                );

                controller.setOnAumentar(
                        () -> alterarAtributo(atributo, 1)
                );

                controller.setOnDiminuir(
                        () -> alterarAtributo(atributo, -1)
                );

                controlesAtributos.put(atributo, controller);

                atributosContainer.getChildren().add(control);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        renderizarFicha();
    }

    private void alterarAtributo(Atributo atributo, int delta) {

        int valorAtual =
                personagemAtual.getValorAtributo(atributo);

        if (delta > 0) {

            if (valorAtual >= 5) return;

            if (personagemAtual.getPontosRestantes() <= 0) return;

        } else {

            if (valorAtual <= 0) return;

        }

        personagemAtual.setValorAtributo(
                atributo,
                valorAtual + delta
        );

        renderizarFicha();
    }


    @FXML
    private void aplicarPontosDisponiveis() {

        try {

            int pontos = Integer.parseInt(
                    pontosDisponiveisField.getText()
            );

            int gastos = personagemAtual.getAgilidade()
                    + personagemAtual.getForca()
                    + personagemAtual.getIntelecto()
                    + personagemAtual.getPresenca()
                    + personagemAtual.getVigor();

            if (pontos < gastos) {

                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setHeaderText("Pontos insuficientes");

                alert.setContentText(
                        "O personagem já possui "
                                + gastos
                                + " pontos distribuídos."
                );

                alert.showAndWait();

                pontosDisponiveisField.setText(
                        String.valueOf(personagemAtual.getPontosDisponiveis())
                );

                return;
            }

            personagemAtual.setPontosDisponiveis(pontos);

            renderizarFicha();

        } catch (NumberFormatException e) {

            pontosDisponiveisField.setText(
                    String.valueOf(personagemAtual.getPontosDisponiveis())
            );

        }

    }



    public void renderizarFicha(){

        //att os atributos
        pontosRestantesLabel.setText(
                String.valueOf(personagemAtual.getPontosRestantes())
        );

        for (Atributo atributo : Atributo.values()) {

            AtributoControlController control =
                    controlesAtributos.get(atributo);

            int valor = personagemAtual.getValorAtributo(atributo);

            control.setValor(valor);

            control.setMaisDesabilitado(
                    valor >= 5 ||
                            personagemAtual.getPontosRestantes() <= 0
            );

            control.setMenosDesabilitado(valor <= 0);

        }
        //att os status

        vidaLabel.setText(
                personagemAtual.getVidaAtual()
                        + " / "
                        + personagemAtual.getVidaMaxima()
        );

        peLabel.setText(
                personagemAtual.getPeAtual()
                        + " / "
                        + personagemAtual.getPeMaximo()
        );

        sanidadeLabel.setText(
                personagemAtual.getSanidadeAtual()
                        + " / "
                        + personagemAtual.getSanidadeMaxima()
        );

        defesaLabel.setText(
                String.valueOf(personagemAtual.getDefesa())
        );

        esquivaLabel.setText(
                String.valueOf(personagemAtual.getEsquiva())
        );

        bloqueioLabel.setText(
                String.valueOf(personagemAtual.getBloqueio())
        );

        deslocamentoLabel.setText(
                personagemAtual.getDeslocamento() + "m"
        );

        limitePeTurnoLabel.setText(
                String.valueOf(personagemAtual.getLimitePeTurno())
        );
    }
}
