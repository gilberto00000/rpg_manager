package com.example.rpg_manager.repository;


import com.example.rpg_manager.database.ConnectionFactory;
import com.example.rpg_manager.model.Habilidade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.ConnectException;
import java.sql.*;

public class HabilidadesRepository {

    public ObservableList<Habilidade> listar(){
        ObservableList<Habilidade> lista =
                FXCollections.observableArrayList();

        String sql = "SELECT * FROM habilidades";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ) {

            while (rs.next()) {
                Habilidade h = new Habilidade();

                h.setId(rs.getInt("id"));
                h.setAvatar(rs.getString("avatar"));
                h.setNome(rs.getString("nome"));
                h.setDescricao(rs.getString("descricao"));

                lista.add(h);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return lista;
    }

    public void salvar(Habilidade habilidade){

        String sql = """
                INSERT INTO habilidades(
                    nome,
                    avatar,
                    descricao
                ) VALUES (?, ?, ?)
                    
                """;

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ){

            ps.setString(1, habilidade.getNome());
            ps.setString(2, habilidade.getAvatar());
            ps.setString(3, habilidade.getDescricao());

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()){
                    habilidade.setId(generatedKeys.getInt(1));
                }
            }

            ps.executeUpdate();

        }catch (SQLException e){
                    e.printStackTrace();
        }

    }

    public void atualizar(Habilidade habilidade) {
        String sql = """
                UPDATE habilidades
                SET 
                    nome = ?,
                    avatar = ?,
                    descricao = ?
                
                WHERE id = ?
                """;
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
                ){

            ps.setString(1, habilidade.getNome());
            ps.setString(2, habilidade.getAvatar());
            ps.setString(3, habilidade.getDescricao());

            int linhas = ps.executeUpdate();

            System.out.println("Linhas upp " + linhas);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void excluir(Integer id){

        String sql = "DELETE FROM habilidades WHERE id = ?";

        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)

                ){

            ps.setInt(1, id);

            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
