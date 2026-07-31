package com.example.rpg_manager.repository;

import com.example.rpg_manager.database.ConnectionFactory;
import com.example.rpg_manager.model.Classes;
import com.example.rpg_manager.model.Personagem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PersonagemRepository {

    private final ClassesRepository classesRepository = new ClassesRepository();

    public ObservableList<Personagem> listar() {

        ObservableList<Personagem> lista =
                FXCollections.observableArrayList();

        String sql = "SELECT * FROM personagem";

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Personagem p = new Personagem();

                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));

                int idClasse = rs.getInt("classe");
                Classes classe = classesRepository.buscarPorId(idClasse);
                p.setClasse(classe);

                p.setNex(rs.getInt("nex"));
                p.setAvatar(rs.getString("avatar"));

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void salvar(Personagem personagem) {

        String sql = """
                INSERT INTO personagem(nome, classe, nex, avatar)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            ps.setString(1, personagem.getNome());
            ps.setInt(2, personagem.getClasse().getId());
            ps.setInt(3, personagem.getNex());
            ps.setString(4, personagem.getAvatar());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                personagem.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Personagem personagem) {

        String sql = """
                UPDATE personagem
                SET nome = ?, classe = ?, nex = ?, avatar = ?
                WHERE id = ?
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, personagem.getNome());
            ps.setInt(2, personagem.getClasse().getId());
            ps.setInt(3, personagem.getNex());
            ps.setString(4, personagem.getAvatar());
            ps.setInt(5, personagem.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(Integer id) {

        String sql = "DELETE FROM personagem WHERE id = ?";

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}