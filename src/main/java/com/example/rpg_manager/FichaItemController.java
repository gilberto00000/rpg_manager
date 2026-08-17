package com.example.rpg_manager;

import com.example.rpg_manager.model.Item;
import com.example.rpg_manager.services.ItemService;
import com.example.rpg_manager.utils.ImageStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FichaItemController implements Initializable {

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

    private final ItemService service =
            new ItemService();

    private Item itemAtual =
            new Item();

    private Runnable aoSalvar;


    @Override
    public void initialize(
            URL location,
            ResourceBundle resources
    ) {

        avatarPreview.setImage(
                new Image(
                        getClass().getResourceAsStream(
                                "/com/example/rpg_manager/images/default-item.png"
                        )
                )
        );
    }


    /*
     * =========================================
     * MODO EDIÇÃO
     * =========================================
     */

    public void setItem(Item item) {

        this.itemAtual = item;

        this.caminhoAvatar =
                item.getAvatar();

        nomeField.setText(
                item.getNome()
        );

        descricaoField.setText(
                item.getDescricao()
        );

        File arquivo = ImageStorage.carregarArquivo(
                caminhoAvatar
        );

        if (arquivo != null && arquivo.exists()) {

            avatarPreview.setImage(
                    new Image(
                            arquivo.toURI().toString()
                    )
            );
        }

        salvarBtn.setText(
                "Atualizar Item"
        );
    }


    /*
     * =========================================
     * AVATAR
     * =========================================
     */

    @FXML
    private void escolherAvatar() {

        FileChooser chooser =
                new FileChooser();

        chooser.setTitle(
                "Escolher imagem do item"
        );

        chooser
                .getExtensionFilters()
                .add(
                        new FileChooser.ExtensionFilter(
                                "Imagens",
                                "*.png",
                                "*.jpg",
                                "*.jpeg"
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

        caminhoAvatar =
                arquivo.getAbsolutePath();

        avatarPreview.setImage(
                new Image(
                        arquivo
                                .toURI()
                                .toString()
                )
        );
    }

    private void prepararAvatarParaSalvar() {

        if (caminhoAvatar == null
                || caminhoAvatar.isBlank()) {
            return;
        }

        if (ImageStorage.ehImagemInterna(caminhoAvatar)) {
            return;
        }

        caminhoAvatar = ImageStorage.salvarImagem(
                new File(caminhoAvatar),
                "itens"
        );
    }


    /*
     * =========================================
     * SALVAR
     * =========================================
     */

    @FXML
    private void salvar() {

        if (!formularioValido()) {
            return;
        }

        prepararAvatarParaSalvar();

        montarItem();

        if (itemAtual.getId() == null) {

            service.salvar(
                    itemAtual
            );

        } else {

            service.atualizar(
                    itemAtual
            );
        }

        if (aoSalvar != null) {
            aoSalvar.run();
        }

        fecharJanela();
    }


    private void montarItem() {

        itemAtual.setNome(
                nomeField
                        .getText()
                        .trim()
        );

        itemAtual.setDescricao(
                descricaoField
                        .getText()
                        .trim()
        );

        itemAtual.setAvatar(
                caminhoAvatar
        );
    }


    /*
     * =========================================
     * VALIDAÇÃO
     * =========================================
     */

    private boolean formularioValido() {

        if (
                nomeField.getText() == null
                        || nomeField
                        .getText()
                        .isBlank()
        ) {

            nomeField.requestFocus();

            return false;
        }

        return true;
    }


    /*
     * =========================================
     * CALLBACK
     * =========================================
     */

    public void setAoSalvar(
            Runnable aoSalvar
    ) {

        this.aoSalvar =
                aoSalvar;
    }


    /*
     * =========================================
     * FECHAR
     * =========================================
     */

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