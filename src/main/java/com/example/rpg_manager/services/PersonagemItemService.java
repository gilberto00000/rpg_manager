package com.example.rpg_manager.services;

import com.example.rpg_manager.model.Item;
import com.example.rpg_manager.model.Personagem;
import com.example.rpg_manager.repository.PersonagemItemRepository;

import java.util.List;

public class PersonagemItemService {

    private final PersonagemItemRepository repository =
            new PersonagemItemRepository();

    public List<Item> listar(Personagem personagem) {

        if (personagem == null || personagem.getId() == null) {
            return List.of();
        }

        return repository.listarPorPersonagem(
                personagem.getId()
        );
    }

    public void sincronizar(Personagem personagem) {

        if (personagem.getId() == null) {
            throw new IllegalStateException(
                    "Personagem precisa estar salvo."
            );
        }

        repository.removerTodas(
                personagem.getId()
        );

        for (Item item : personagem.getItens()) {

            repository.associar(
                    personagem.getId(),
                    item.getId()
            );
        }
    }
}
