package com.example.rpg_manager.repository;

import com.example.rpg_manager.database.ConnectionFactory;
import com.example.rpg_manager.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonagemItemRepository {

    public void associar(int personagemId, int itemId) {

        String sql = """
        INSERT INTO personagem_item (
            personagem_id,
            item_id
        ) VALUES (?, ?)
        """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, personagemId);
            ps.setInt(2, itemId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao associar item ao personagem",
                    e
            );
        }
    }

    public void removerTodas(int personagemId) {

        String sql = """
            DELETE FROM personagem_item
            WHERE personagem_id = ?
            """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, personagemId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao remover itens do personagem",
                    e
            );
        }
    }

    public List<Item> listarPorPersonagem(int personagemId) {

        List<Item> itens = new ArrayList<>();

        String sql = """
            SELECT i.*
            FROM itens i
            INNER JOIN personagem_item pi
                ON pi.item_id = i.id
            WHERE pi.personagem_id = ?
            ORDER BY i.nome
            """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, personagemId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Item item = new Item();

                    item.setId(rs.getInt("id"));
                    item.setAvatar(rs.getString("avatar"));
                    item.setNome(rs.getString("nome"));
                    item.setDescricao(rs.getString("descricao"));

                    itens.add(item);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar itens do personagem",
                    e
            );
        }

        return itens;
    }
}