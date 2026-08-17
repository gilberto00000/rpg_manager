package com.example.rpg_manager.repository;

import com.example.rpg_manager.database.ConnectionFactory;
import com.example.rpg_manager.model.Cenario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CenarioRepository {

    public ObservableList<Cenario> listar() {

        ObservableList<Cenario> lista =
                FXCollections.observableArrayList();

        String sql = """
                SELECT
                    id,
                    nome,
                    imagem
                FROM cenario
                ORDER BY nome
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Cenario cenario = new Cenario();

                cenario.setId(
                        rs.getInt("id")
                );

                cenario.setNome(
                        rs.getString("nome")
                );

                cenario.setImagem(
                        rs.getString("imagem")
                );

                lista.add(cenario);
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao listar cenários",
                    e
            );
        }

        return lista;
    }


    public Cenario buscarPorId(Integer id) {

        String sql = """
                SELECT
                    id,
                    nome,
                    imagem
                FROM cenario
                WHERE id = ?
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Cenario cenario =
                            new Cenario();

                    cenario.setId(
                            rs.getInt("id")
                    );

                    cenario.setNome(
                            rs.getString("nome")
                    );

                    cenario.setImagem(
                            rs.getString("imagem")
                    );

                    return cenario;
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao buscar cenário com ID " + id,
                    e
            );
        }

        return null;
    }


    public void salvar(Cenario cenario) {

        String sql = """
                INSERT INTO cenario (
                    nome,
                    imagem
                )
                VALUES (?, ?)
                """;

        try (
                Connection con = ConnectionFactory.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            ps.setString(
                    1,
                    cenario.getNome()
            );

            ps.setString(
                    2,
                    cenario.getImagem()
            );

            int linhas =
                    ps.executeUpdate();

            if (linhas == 0) {

                throw new SQLException(
                        "Nenhum cenário foi inserido."
                );
            }

            try (
                    ResultSet generatedKeys =
                            ps.getGeneratedKeys()
            ) {

                if (generatedKeys.next()) {

                    cenario.setId(
                            generatedKeys.getInt(1)
                    );

                } else {

                    throw new SQLException(
                            "O banco não retornou o ID do cenário."
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao salvar cenário",
                    e
            );
        }
    }


    public void atualizar(Cenario cenario) {

        String sql = """
                UPDATE cenario
                SET
                    nome = ?,
                    imagem = ?
                WHERE id = ?
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    cenario.getNome()
            );

            ps.setString(
                    2,
                    cenario.getImagem()
            );

            ps.setInt(
                    3,
                    cenario.getId()
            );

            int linhas =
                    ps.executeUpdate();

            if (linhas == 0) {

                throw new SQLException(
                        "Nenhum cenário encontrado com ID "
                                + cenario.getId()
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao atualizar cenário",
                    e
            );
        }
    }


    public void excluir(Integer id) {

        String sql = """
                DELETE FROM cenario
                WHERE id = ?
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    id
            );

            int linhas =
                    ps.executeUpdate();

            if (linhas == 0) {

                throw new SQLException(
                        "Nenhum cenário encontrado com ID " + id
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao excluir cenário",
                    e
            );
        }
    }
}