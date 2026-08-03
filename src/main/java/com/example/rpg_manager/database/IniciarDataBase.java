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
                        avatar TEXT,
                    
                        classe INTEGER,
                        nex INTEGER DEFAULT 0,
                    
                        pontos_disponiveis INTEGER,
                    
                        agilidade INTEGER DEFAULT 0,
                        forca INTEGER DEFAULT 0,
                        intelecto INTEGER DEFAULT 0,
                        presenca INTEGER DEFAULT 0,
                        vigor INTEGER DEFAULT 0,
            
                        vida_atual INTEGER DEFAULT 0,
                        pe_atual INTEGER DEFAULT 0,
                        sanidade_atual INTEGER DEFAULT 0,
                        pd_atual INTEGER DEFAULT 0,
            
                        rodadas_morrendo INTEGER DEFAULT 0
                    );
            """);

        }   catch (SQLException e){
            e.printStackTrace();
            }

    }
}
