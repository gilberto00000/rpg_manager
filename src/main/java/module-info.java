module com.example.rpg_manager {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.rpg_manager.model to javafx.base;
    opens com.example.rpg_manager to javafx.fxml;
    exports com.example.rpg_manager;
}