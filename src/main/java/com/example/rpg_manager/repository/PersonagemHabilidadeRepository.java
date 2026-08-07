package com.example.rpg_manager.repository;

import com.example.rpg_manager.database.ConnectionFactory;
import com.example.rpg_manager.model.Habilidade;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonagemHabilidadeRepository {
    private final HabilidadesRepository habilidadesRepository =
            new HabilidadesRepository();

    public void associar(int personagemId, int habilidadeId){
        String sql = """
                INSERT OR IGNORE INTO personagem_habilidade (
                    personagem_id,
                    habilidade_id
                ) VALUES (?, ?)
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
                ){
            ps.setInt(1, personagemId);
            ps.setInt(2, habilidadeId);

            ps.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(
                    "Erro ao associar habilidade ao personagem", e
            );
        }
    }

    public void remover(int personagemId, int habilidadeId){
        String sql = """
                DELETE FROM personagem_habilidade
                WHERE personagem_id = ?
                AND habilidade_id = ?
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
                ){

            ps.setInt(1, personagemId);
            ps.setInt(2, habilidadeId);

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(
                    "Erro ao remover habilidade", e
            );
        }
    }

    public List<Habilidade> listarPorPersonagem(int personagemId) {
        List<Habilidade> habilidades = new ArrayList<>();

        String sql = """
                
                SELECT h.*
                FROM habilidades h
                INNER JOIN personagem_habilidade ph
                    ON ph.personagem_id = h.id
                WHERE ph.personagem_id = ?
                ORDER BY h.nome
                
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setInt(1, personagemId);

            try (
                    ResultSet rs = ps.executeQuery()
            ){
                while (rs.next()){
                    Habilidade habilidade = new Habilidade();

                    habilidade.setId(rs.getInt("id"));
                    habilidade.setNome(rs.getString("nome"));
                    habilidade.setDescricao(
                        rs.getString("descrição")
                    );
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(
                    "Erro ao listar as habilidades do personagem",
                    e
            );
        }


        return habilidades;
    }

    public boolean personagemPossui(
            int personagemId,
            int habilidadeId
    ){
        String sql = """
                SELECT 1
                FROM personagem_habilidade
                WHERE personagem_id = ?
                 AND habilidade_id = ?
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setInt(1, personagemId);
            ps.setInt(2, habilidadeId);

            try (
                    ResultSet rs = ps.executeQuery())
            {
                return rs.next();

            }
        } catch (SQLException e){
            throw new RuntimeException(
                    "Erro ao verificar habilidades",
                    e
            );
        }
    }
}
