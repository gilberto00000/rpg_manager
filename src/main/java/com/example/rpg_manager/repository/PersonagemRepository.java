package com.example.rpg_manager.repository;

import com.example.rpg_manager.model.Classes;
import com.example.rpg_manager.model.Personagem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonagemRepository {

    private final ObservableList<Personagem> personagens =
            FXCollections.observableArrayList();

    public void salvar(String nome, int nivel, Classes classe){
        Personagem p = new Personagem(
            nome,
            nivel,
            classe
        );
        personagens.add(p);
    }

    public ObservableList<Personagem> listar(){
        return personagens;
    }
}
