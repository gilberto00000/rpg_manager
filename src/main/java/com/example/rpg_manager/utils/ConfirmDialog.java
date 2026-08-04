package com.example.rpg_manager.utils;

import com.example.rpg_manager.ConfirmDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

public final class ConfirmDialog {

    private ConfirmDialog() {
    }

    public static boolean show(
            Window owner,
            String titulo,
            String mensagem
    ) {
        return show(
                owner,
                titulo,
                mensagem,
                "Confirmar"
        );
    }

    public static boolean show(
            Window owner,
            String titulo,
            String mensagem,
            String textoConfirmar
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    ConfirmDialog.class.getResource(
                            "/com/example/rpg_manager/fxml/ConfirmDialog.fxml"
                    )
            );

            Parent root = loader.load();

            ConfirmDialogController controller = loader.getController();

            controller.configurar(
                    titulo,
                    mensagem,
                    textoConfirmar
            );

            Stage dialogStage = new Stage();


            dialogStage.initStyle(StageStyle.TRANSPARENT);

            if (owner != null) {
                dialogStage.initOwner(owner);
                dialogStage.initModality(Modality.WINDOW_MODAL);
            }


            root.setStyle("-fx-background-color: transparent;");


            Scene scene = new Scene(root, Color.TRANSPARENT);

            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            dialogStage.showAndWait();

            return controller.isConfirmado();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao abrir diálogo de confirmação",
                    e
            );
        }
    }
}
