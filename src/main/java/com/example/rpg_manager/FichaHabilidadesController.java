package com.example.rpg_manager;

import com.example.rpg_manager.model.Habilidade;
import com.example.rpg_manager.services.HabilidadesService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FichaHabilidadesController implements Initializable {

    @FXML
    private Button salvarBtn;
    @FXML
    private Button voltarBtn;
    @FXML
    private ImageView avatarPreview;
    @FXML
    private TextField nomeField;
    @FXML
    private TextArea descricaoField;

    private String caminhoAvatar;

    private final HabilidadesService service = new HabilidadesService();
    private Habilidade habilidadeAtual = new Habilidade();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avatarPreview.setImage(
                new Image(
                        getClass().getResourceAsStream(
                                "/com/example/rpg_manager/images/default-avatar.png"
                        )
                )
        );

        habilidadeAtual = new Habilidade();

    }

    @FXML
    private void voltar() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("fxml/main.fxml")
        );

        Stage stage = (Stage) salvarBtn.getScene().getWindow();
        stage.close();
    }

    public void setHabilidade(Habilidade habilidade){
        this.habilidadeAtual = habilidade;
        this.caminhoAvatar = habilidade.getAvatar();

        if (caminhoAvatar != null && !caminhoAvatar.isBlank()){
            avatarPreview.setImage(
                    new Image(new File(caminhoAvatar).toURI().toString())
            );
        }

        preencherCampos();
    }

    private void preencherCampos() {
        nomeField.setText(habilidadeAtual.getNome());

        descricaoField.setText(habilidadeAtual.getDescricao());
    }

    @FXML
    private void escolherAvatar(){
        FileChooser chooser = new FileChooser();

        chooser.setTitle("Escolher imagem");

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

    private void montarHabilidade(){
        if (habilidadeAtual == null){
            habilidadeAtual = new Habilidade();
        }

        habilidadeAtual.setNome(nomeField.getText());
        habilidadeAtual.setDescricao(descricaoField.getText());
        habilidadeAtual.setAvatar(caminhoAvatar);

    }

    @FXML
    private void salvar(){

        montarHabilidade();

        if (habilidadeAtual.getId() == null){
            service.salvar(habilidadeAtual);
        }else {
            service.atualizar(habilidadeAtual);
        }

        if (aoSalvar !=null){
            aoSalvar.run();
        }

        fecharJanela();
    }

    private Runnable aoSalvar;

    public void setAoSalvar(Runnable aoSalvar){
        this.aoSalvar = aoSalvar;
    }

    public void fecharJanela(){
        Stage stage = (Stage) salvarBtn.getScene().getWindow();
        stage.close();
    }

}
