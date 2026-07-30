package com.example.rpg_manager.repository;

import com.example.rpg_manager.database.ConnectionFactory;
import com.example.rpg_manager.model.Classes;
import com.example.rpg_manager.model.Personagem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonagemRepository {


    private ClassesRepository classesRepository = new ClassesRepository();

    public ObservableList<Personagem> listar(){
        ObservableList<Personagem> lista = FXCollections.observableArrayList();

        String sql = "SELECT * FROM personagem";

        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){

                String nome = rs.getString("nome");

                int nivel = rs.getInt("nivel");

                int idClasse = rs.getInt("classe");

                Classes classe = classesRepository.buscarPorId(idClasse);

                Personagem personagem = new Personagem(
                        rs.getInt("id"),
                        nome,
                        nivel,
                        classe
                );

                personagem.setAvatar(rs.getString("avatar"));

                lista.add(personagem);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public void salvar(Personagem personagem){
        String sql = """
                INSERT INTO personagem(nome, nivel, classe, avatar)
                VALUES (?, ?, ?, ?)
                """;

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setString(1, personagem.getNome());
            ps.setInt(2, personagem.getNivel());

            //isso vai dar uma dor de cabeça tão grande
            ps.setInt(3, personagem.getClasse().getId());

            ps.setString(4, personagem.getAvatar());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void atualizar(Personagem personagem){
        String sql = """
                UPDATE personagem
                SET nome = ?, nivel = ?, classe = ?, avatar = ?
                WHERE id = ?
                """;

        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
                ) {

            ps.setString(1, personagem.getNome());
            ps.setInt(2, personagem.getNivel());
            ps.setInt(3, personagem.getClasse().getId());
            ps.setString(4, personagem.getAvatar());
            ps.setInt(5, personagem.getId());

            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void excluir(Integer id){
        String sql = "DELETE FROM personagem WHERE id = ?";

        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps =  con.prepareStatement(sql)

                ){

            ps.setInt(1, id);
            ps.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
