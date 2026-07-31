package com.example.rpg_manager.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class IniciarDataBase {

    public static void iniciar(){

        try (Connection con = ConnectionFactory.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute("""
                    CREATE TABLE personagem (
                    
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                    
                        nome TEXT NOT NULL,
                        avatar TEXT,
                    
                        classe INTEGER,
                        nex INTEGER,
                    
                        pontos_disponiveis INTEGER,
                    
                        agilidade INTEGER,
                        forca INTEGER,
                        intelecto INTEGER,
                        presenca INTEGER,
                        vigor INTEGER,
                    
                        vida_atual INTEGER,
                        pe_atual INTEGER,
                        sanidade_atual INTEGER,
                        pd_atual INTEGER,
                    
                        rodadas_morrendo INTEGER
                    );
            """);

        }   catch (SQLException e){
            e.printStackTrace();
            }

    }
}
