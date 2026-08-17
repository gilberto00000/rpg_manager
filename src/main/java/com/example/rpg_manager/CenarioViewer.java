package com.example.rpg_manager;

import com.example.rpg_manager.model.Cenario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class CenarioViewer {

    private static Stage stage;

    private static CenarioViewerController controller;


    private CenarioViewer() {
    }


    public static void exibir(
            Cenario cenario
    ) {

        try {

            if (stage == null) {

                criarJanela();
            }

            controller.setCenario(
                    cenario
            );


            if (!stage.isShowing()) {
                stage.show();
            }


            if (stage.isIconified()) {
                stage.setIconified(false);
            }


            stage.toFront();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao abrir visualizador de cenário",
                    e
            );
        }
    }


    private static void criarJanela()
            throws IOException {

        FXMLLoader loader =
                new FXMLLoader(
                        CenarioViewer.class
                                .getResource(
                                        "/com/example/rpg_manager/fxml/cenario-viewer.fxml"
                                )
                );

        Parent root =
                loader.load();

        controller =
                loader.getController();

        stage =
                new Stage();

        stage.setTitle(
                "Cenário"
        );

        stage.setScene(
                new Scene(
                        root,
                        1280,
                        720
                )
        );

        stage.setMinWidth(
                640
        );

        stage.setMinHeight(
                360
        );


        stage.setOnHidden(event -> {

            stage = null;
            controller = null;

        });
    }
}