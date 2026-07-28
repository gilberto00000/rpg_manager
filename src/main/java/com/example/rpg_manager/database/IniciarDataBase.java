package com.example.rpg_manager.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class IniciarDataBase {

    public static void iniciar(){

        try (Connection con = ConnectionFactory.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS personagem (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    nivel INTEGER NOT NULL,
                    classe TEXT NOT NULL
                    );
            """);

        }   catch (SQLException e){
            e.printStackTrace();
            }

    }
}
