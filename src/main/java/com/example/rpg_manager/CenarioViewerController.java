package com.example.rpg_manager;

import com.example.rpg_manager.model.Cenario;
import com.example.rpg_manager.utils.ImageStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CenarioViewerController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private ImageView cenarioImage;


    @Override
    public void initialize(
            URL location,
            ResourceBundle resources
    ) {

        cenarioImage
                .fitWidthProperty()
                .bind(
                        root.widthProperty()
                );

        cenarioImage
                .fitHeightProperty()
                .bind(
                        root.heightProperty()
                );
    }


    public void setCenario(
            Cenario cenario
    ) {

        if (cenario == null) {
            return;
        }

        File arquivo =
                ImageStorage.carregarArquivo(
                        cenario.getImagem()
                );

        if (arquivo == null
                || !arquivo.exists()) {

            cenarioImage.setImage(null);
            return;
        }

        cenarioImage.setImage(
                new Image(
                        arquivo
                                .toURI()
                                .toString()
                )
        );
    }
}