package com.example.rpg_manager.repository;


import com.example.rpg_manager.database.ConnectionFactory;
import com.example.rpg_manager.model.Habilidade;
import com.example.rpg_manager.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.ConnectException;
import java.sql.*;

public class ItemRepository {

    public ObservableList<Item> listar(){
        ObservableList<Item> lista =
                FXCollections.observableArrayList();

        String sql = "SELECT * FROM itens";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Item h = new Item();

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

    public void salvar(Item item){

        String sql = """
                INSERT INTO itens(
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

            ps.setString(1, item.getNome());
            ps.setString(2, item.getAvatar());
            ps.setString(3, item.getDescricao());

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()){
                    item.setId(generatedKeys.getInt(1));
                }
            }

            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void atualizar(Item item) {
        String sql = """
                UPDATE itens
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

            ps.setString(1, item.getNome());
            ps.setString(2, item.getAvatar());
            ps.setString(3, item.getDescricao());

            int linhas = ps.executeUpdate();

            System.out.println("Linhas upp " + linhas);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void excluir(Integer id){

        String sql = "DELETE FROM itens WHERE id = ?";

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
