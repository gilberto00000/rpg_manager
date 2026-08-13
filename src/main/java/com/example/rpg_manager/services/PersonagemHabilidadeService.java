package com.example.rpg_manager.services;

import com.example.rpg_manager.model.Habilidade;
import com.example.rpg_manager.model.Personagem;
import com.example.rpg_manager.repository.PersonagemHabilidadeRepository;

import java.util.List;

public class PersonagemHabilidadeService {

    private final PersonagemHabilidadeRepository repository =
            new PersonagemHabilidadeRepository();

    public void associar(
            Personagem personagem,
            Habilidade habilidade
    ) {
        validarIds(personagem, habilidade);

        repository.associar(
                personagem.getId(),
                habilidade.getId()
        );
    }

    public void remover(
            Personagem personagem,
            Habilidade habilidade
    ) {
        validarIds(personagem, habilidade);

        repository.remover(
                personagem.getId(),
                habilidade.getId()
        );
    }

    public List<Habilidade> listar(Personagem personagem) {
        if (personagem == null || personagem.getId() == null) {
            return List.of();
        }

        return repository.listarPorPersonagem(
                personagem.getId()
        );
    }

    private void validarIds(
            Personagem personagem,
            Habilidade habilidade
    ) {
        if (personagem == null || personagem.getId() == null) {
            throw new IllegalArgumentException(
                    "O personagem precisa estar salvo."
            );
        }

        if (habilidade == null || habilidade.getId() == null) {
            throw new IllegalArgumentException(
                    "A habilidade precisa estar salva."
            );
        }
    }

    public void sincronizar(Personagem personagem) {

        if (personagem.getId() == null) {
            throw new IllegalStateException(
                    "Personagem precisa estar salvo antes de sincronizar habilidades."
            );
        }

        repository.removerTodas(
                personagem.getId()
        );

        for (Habilidade habilidade :
                personagem.getHabilidades()) {

            repository.associar(
                    personagem.getId(),
                    habilidade.getId()
            );
        }
    }
}