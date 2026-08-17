package com.example.rpg_manager;

import com.example.rpg_manager.utils.ImageStorage;
import com.example.rpg_manager.model.Cenario;
import com.example.rpg_manager.services.CenarioService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FichaCenarioController implements Initializable {

    @FXML
    private Button salvarBtn;

    @FXML
    private Button voltarBtn;

    @FXML
    private TextField nomeField;

    @FXML
    private ImageView imagemPreview;

    private final CenarioService service =
            new CenarioService();

    private Cenario cenarioAtual =
            new Cenario();

    private String caminhoImagem;

    private Runnable aoSalvar;


    @Override
    public void initialize(
            URL location,
            ResourceBundle resources
    ) {

        imagemPreview.setImage(null);
    }


    public void setCenario(
            Cenario cenario
    ) {

        this.cenarioAtual =
                cenario;

        nomeField.setText(
                cenario.getNome()
        );

        caminhoImagem =
                cenario.getImagem();


        File arquivo =
                ImageStorage.carregarArquivo(
                        caminhoImagem
                );


        if (arquivo != null
                && arquivo.exists()) {

            imagemPreview.setImage(
                    new Image(
                            arquivo
                                    .toURI()
                                    .toString()
                    )
            );
        }


        salvarBtn.setText(
                "Atualizar cenário"
        );
    }


    @FXML
    private void escolherImagem() {

        FileChooser chooser =
                new FileChooser();

        chooser.setTitle(
                "Escolher imagem do cenário"
        );

        chooser
                .getExtensionFilters()
                .add(
                        new FileChooser.ExtensionFilter(
                                "Imagens",
                                "*.png",
                                "*.jpg",
                                "*.jpeg",
                                "*.webp"
                        )
                );

        File arquivo =
                chooser.showOpenDialog(
                        nomeField
                                .getScene()
                                .getWindow()
                );

        if (arquivo == null) {
            return;
        }

        caminhoImagem =
                arquivo.getAbsolutePath();

        imagemPreview.setImage(
                new Image(
                        arquivo
                                .toURI()
                                .toString()
                )
        );
    }


    @FXML
    private void salvar() {

        if (!formularioValido()) {
            return;
        }

        prepararImagemParaSalvar();

        montarCenario();

        if (cenarioAtual.getId() == null) {

            service.salvar(
                    cenarioAtual
            );

        } else {

            service.atualizar(
                    cenarioAtual
            );
        }

        if (aoSalvar != null) {
            aoSalvar.run();
        }

        fecharJanela();
    }

    private void prepararImagemParaSalvar() {

        if (caminhoImagem == null
                || caminhoImagem.isBlank()) {

            return;
        }

        /*
         * Se já está dentro de data/images/,
         * não copia novamente.
         */
        if (ImageStorage.ehImagemInterna(
                caminhoImagem
        )) {
            return;
        }

        File original =
                new File(caminhoImagem);

        caminhoImagem =
                ImageStorage.salvarImagem(
                        original,
                        "cenarios"
                );
    }

    private void montarCenario() {

        cenarioAtual.setNome(
                nomeField
                        .getText()
                        .trim()
        );

        cenarioAtual.setImagem(
                caminhoImagem
        );
    }


    private boolean formularioValido() {

        if (nomeField.getText() == null
                || nomeField.getText().isBlank()) {

            nomeField.requestFocus();
            return false;
        }

        if (caminhoImagem == null
                || caminhoImagem.isBlank()) {

            return false;
        }

        return true;
    }


    public void setAoSalvar(
            Runnable aoSalvar
    ) {

        this.aoSalvar =
                aoSalvar;
    }


    @FXML
    private void voltar() {

        fecharJanela();
    }


    private void fecharJanela() {

        Stage stage =
                (Stage) salvarBtn
                        .getScene()
                        .getWindow();

        stage.close();
    }
}