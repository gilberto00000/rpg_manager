package com.example.rpg_manager.services;

import com.example.rpg_manager.model.Habilidade;
import com.example.rpg_manager.model.Item;
import com.example.rpg_manager.repository.HabilidadesRepository;
import com.example.rpg_manager.repository.ItemRepository;
import javafx.collections.ObservableList;

public class ItemService {

    private final ItemRepository repository = new ItemRepository();

    public void salvar(Item item){

        if (item.getNome().isBlank()){
            throw new IllegalArgumentException("Insira um nome");
        }

        repository.salvar(item);
    }

    public ObservableList<Item> listar() {return repository.listar();}

    public void atualizar(Item item) { repository.atualizar(item);}

    public void excluir(Integer id) {repository.excluir(id);}
}
