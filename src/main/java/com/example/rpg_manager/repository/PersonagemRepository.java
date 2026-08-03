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

                p.setAvatar(rs.getString("avatar"));
                p.setNex(rs.getInt("nex"));

                p.setPontosDisponiveis(
                        rs.getInt("pontos_disponiveis")
                );

                p.setAgilidade(rs.getInt("agilidade"));
                p.setForca(rs.getInt("forca"));
                p.setIntelecto(rs.getInt("intelecto"));
                p.setPresenca(rs.getInt("presenca"));
                p.setVigor(rs.getInt("vigor"));

                p.setVidaAtual(rs.getInt("vida_atual"));
                p.setPeAtual(rs.getInt("pe_atual"));
                p.setSanidadeAtual(rs.getInt("sanidade_atual"));

                p.setRodadasMorrendo(
                        rs.getInt("rodadas_morrendo")
                );

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void salvar(Personagem personagem) {

        String sql = """
                INSERT INTO personagem(
                
                    nome,
                    avatar,
                    classe,
                    nex,
                
                    pontos_disponiveis,
                
                    agilidade,
                    forca,
                    intelecto,
                    presenca,
                    vigor,
                
                    vida_atual,
                    pe_atual,
                    sanidade_atual,
                
                    rodadas_morrendo
                
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            ps.setString(1, personagem.getNome());
            ps.setString(2, personagem.getAvatar());
            ps.setInt(3, personagem.getClasse().getId());
            ps.setInt(4, personagem.getNex());

            ps.setInt(5, personagem.getPontosDisponiveis());

            ps.setInt(6, personagem.getAgilidade());
            ps.setInt(7, personagem.getForca());
            ps.setInt(8, personagem.getIntelecto());
            ps.setInt(9, personagem.getPresenca());
            ps.setInt(10, personagem.getVigor());

            ps.setInt(11, personagem.getVidaAtual());
            ps.setInt(12, personagem.getPeAtual());
            ps.setInt(13, personagem.getSanidadeAtual());
            ps.setInt(14, personagem.getRodadasMorrendo());

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
                SET
                    nome = ?,
                    avatar = ?,
                    classe = ?,
                    nex = ?,
                
                    pontos_disponiveis = ?,
                
                    agilidade = ?,
                    forca = ?,
                    intelecto = ?,
                    presenca = ?,
                    vigor = ?,
                
                    vida_atual = ?,
                    pe_atual = ?,
                    sanidade_atual = ?,
                
                    rodadas_morrendo = ?
                
                WHERE id = ?
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, personagem.getNome());
            ps.setString(2, personagem.getAvatar());
            ps.setInt(3, personagem.getClasse().getId());
            ps.setInt(4, personagem.getNex());

            ps.setInt(5, personagem.getPontosDisponiveis());

            ps.setInt(6, personagem.getAgilidade());
            ps.setInt(7, personagem.getForca());
            ps.setInt(8, personagem.getIntelecto());
            ps.setInt(9, personagem.getPresenca());
            ps.setInt(10, personagem.getVigor());

            ps.setInt(11, personagem.getVidaAtual());
            ps.setInt(12, personagem.getPeAtual());
            ps.setInt(13, personagem.getSanidadeAtual());

            ps.setInt(14, personagem.getRodadasMorrendo());

            ps.setInt(15, personagem.getId());

            ps.executeUpdate();

            int linhas = ps.executeUpdate();

            System.out.println("Linhas atualizadas: " + linhas);

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