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
                        nome,
                        nivel,
                        classe
                );

                lista.add(personagem);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public void salvar(Personagem personagem){
        String sql = """
                INSERT INTO personagem(nome, nivel, classe)
                VALUES (?, ?,?)
                """;

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setString(1, personagem.getNome());
            ps.setInt(2, personagem.getNivel());

            //isso vai dar uma dor de cabeça tão grande
            ps.setInt(3, personagem.getClasse().getId());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
