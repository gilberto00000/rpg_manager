package com.example.rpg_manager.repository;

import com.example.rpg_manager.model.Classes;
import com.example.rpg_manager.model.Personagem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonagemRepository {

    private final ObservableList<Personagem> personagens =
            FXCollections.observableArrayList();

    public void salvar(Personagem personagem){
        personagens.add(personagem);
    }

    public ObservableList<Personagem> listar(){
        return personagens;
    }
}
