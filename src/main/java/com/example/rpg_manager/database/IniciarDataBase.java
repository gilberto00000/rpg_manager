package com.example.rpg_manager.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class IniciarDataBase {

    public static void iniciar() {

        try (Connection con = ConnectionFactory.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");

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
                    
                                rodadas_morrendo INTEGER DEFAULT 0
                            );
                    """);

            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS personagem_item (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                    
                            personagem_id INTEGER NOT NULL,
                            item_id INTEGER NOT NULL,
                    
                            FOREIGN KEY (personagem_id)
                                REFERENCES personagem(id)
                                ON DELETE CASCADE,
                    
                            FOREIGN KEY (item_id)
                                REFERENCES itens(id)
                                ON DELETE CASCADE
                        );
                    """);

            stmt.execute("""
                    
                    CREATE TABLE IF NOT EXISTS habilidades(
                    
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                    
                        nome TEXT NOT NULL,
                        avatar TEXT,
                        descricao TEXT
                        );
                    )
                    
                    
                    """);

            stmt.execute("""
                    
                    CREATE TABLE IF NOT EXISTS itens(
                    
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                    
                        nome TEXT NOT NULL,
                        avatar TEXT,
                        descricao TEXT
                        );
                    )
                    
                    
                    """);


            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS personagem_habilidade (
                            personagem_id INTEGER NOT NULL,
                            habilidade_id INTEGER NOT NULL,
                    
                            PRIMARY KEY (personagem_id, habilidade_id),
                    
                            FOREIGN KEY (personagem_id)
                                REFERENCES personagem(id)
                                ON DELETE CASCADE,
                    
                            FOREIGN KEY (habilidade_id)
                                REFERENCES habilidade(id)
                                ON DELETE CASCADE
                        )
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS cenario (
                    
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                    
                        nome TEXT NOT NULL,
                    
                        imagem TEXT NOT NULL
                    )
                    """);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
