package com.example.rpg_manager.services;

import com.example.rpg_manager.model.Personagem;
import com.example.rpg_manager.repository.PersonagemRepository;
import javafx.collections.ObservableList;

public class PersonagemServices {


    private final PersonagemRepository repository = new PersonagemRepository();

    public void salvar(Personagem personagem){

        if (personagem.getNome().isBlank()){
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        repository.salvar(personagem);
    }

    public ObservableList<Personagem> listar(){
        return repository.listar();
    }

    public void atualizar(Personagem personagem) {
        repository.atualizar(personagem);
    }

    public void excluir(Integer id){
        repository.excluir(id);
    }
}
