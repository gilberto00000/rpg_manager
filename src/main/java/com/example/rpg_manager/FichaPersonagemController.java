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
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;


public class FichaPersonagemController implements Initializable {


    @FXML
    private Button voltarBtn;
    @FXML
    private ProgressBar vidaProgress;
    @FXML
    private ProgressBar peProgress;
    @FXML
    private ProgressBar sanidadeProgress;
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
    private Label limitePePorTurnoLabel;
    @FXML
    private Label rodadasMorrendoLabel;
    @FXML
    private Label limitePeLabel;

    private final Map<Atributo, AtributoControlController> controlesAtributos =
            new EnumMap<>(Atributo.class);


    @FXML
    private void voltar() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("fxml/main.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) salvarBtn.getScene().getWindow();
        stage.close();
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
        this.caminhoAvatar = personagem.getAvatar();

        if (caminhoAvatar != null && !caminhoAvatar.isBlank()){

            avatarPreview.setImage(
                    new Image(new File(caminhoAvatar).toURI().toString())
            );
        }

        preencherCampos();
        renderizarFicha();
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
        personagemAtual.setAvatar(caminhoAvatar);

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

        if ( aoSalvar != null){
            aoSalvar.run();
        }

        fecharJanela();

    }

    private Runnable aoSalvar;

    public void setAoSalvar(Runnable aoSalvar){
        this.aoSalvar = aoSalvar;
    }

    private void fecharJanela() {
        Stage stage = (Stage) salvarBtn.getScene().getWindow();
        stage.close();
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

    private double calcularProgresso(int atual, int maximo) {
        if (maximo <= 0) {
            return 0;
        }

        double progresso = (double) atual / maximo;

        return Math.max(0, Math.min(1, progresso));
    }

    public void atualizarStatus(){
        int vidaAtual = personagemAtual.getVidaAtual();
        int vidaMaxima = personagemAtual.getVidaMaxima();

        int peAtual = personagemAtual.getPeAtual();
        int peMaximo = personagemAtual.getPeMaximo();

        int sanidadeAtual = personagemAtual.getSanidadeAtual();
        int sanidadeMaxima = personagemAtual.getSanidadeMaxima();

        vidaLabel.setText(vidaAtual + " / " + vidaMaxima);
        peLabel.setText(peAtual + " / " + peMaximo);
        sanidadeLabel.setText(sanidadeAtual + " / " + sanidadeMaxima);

        vidaProgress.setProgress(calcularProgresso(vidaAtual, vidaMaxima));
        peProgress.setProgress(calcularProgresso(peAtual, peMaximo));
        sanidadeProgress.setProgress(
                calcularProgresso(sanidadeAtual, sanidadeMaxima)
        );

        rodadasMorrendoLabel.setText(
                personagemAtual.getRodadasMorrendo() + " / 3"
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
                personagemAtual.getDeslocamento() + " m"
        );

        limitePeLabel.setText(
                String.valueOf(personagemAtual.getLimitePePorTurno())
        );
    }

    private void atualizarAtributos(){
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
    }

    public void renderizarFicha(){
        atualizarAtributos();
        atualizarStatus();

    }

    // controle de status atual


    // controle de pv
    @FXML
    private void diminuirVida() {
        int atual = personagemAtual.getVidaAtual();

        if (atual > 0) {
            personagemAtual.setVidaAtual(atual - 1);
            renderizarFicha();
        }
    }

    @FXML
    private void aumentarVida() {
        int atual = personagemAtual.getVidaAtual();
        int maxima = personagemAtual.getVidaMaxima();

        if (atual < maxima) {
            personagemAtual.setVidaAtual(atual + 1);
            renderizarFicha();
        }
    }

    //controle de pe

    @FXML
    private void diminuirPe() {
        int atual = personagemAtual.getPeAtual();

        if (atual > 0) {
            personagemAtual.setPeAtual(atual - 1);
            renderizarFicha();
        }
    }

    @FXML
    private void aumentarPe() {
        int atual = personagemAtual.getPeAtual();
        int maximo = personagemAtual.getPeMaximo();

        if (atual < maximo) {
            personagemAtual.setPeAtual(atual + 1);
            renderizarFicha();
        }
    }

    //controle de sanidade

    @FXML
    private void diminuirSanidade() {
        int atual = personagemAtual.getSanidadeAtual();

        if (atual > 0) {
            personagemAtual.setSanidadeAtual(atual - 1);
            renderizarFicha();
        }
    }

    @FXML
    private void aumentarSanidade() {
        int atual = personagemAtual.getSanidadeAtual();
        int maxima = personagemAtual.getSanidadeMaxima();

        if (atual < maxima) {
            personagemAtual.setSanidadeAtual(atual + 1);
            renderizarFicha();
        }
    }

    //controle de rodadas em momento

    @FXML
    private void diminuirRodadasMorrendo() {
        int rodadas = personagemAtual.getRodadasMorrendo();

        if (rodadas > 0) {
            personagemAtual.setRodadasMorrendo(rodadas -1);
            renderizarFicha();
        }
    }

    @FXML
    private void aumentarRodadasMorrendo(){
        int rodadas = personagemAtual.getRodadasMorrendo();

        if (rodadas < 3) {
            personagemAtual.setRodadasMorrendo(rodadas + 1);
            renderizarFicha();
        }
    }

}
