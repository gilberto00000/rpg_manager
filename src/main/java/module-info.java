module com.example.rpg_manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires java.sql;
    requires java.desktop;

    requires org.xerial.sqlitejdbc;

    opens com.example.rpg_manager.model to javafx.base;
    opens com.example.rpg_manager to javafx.fxml;

    exports com.example.rpg_manager;
}