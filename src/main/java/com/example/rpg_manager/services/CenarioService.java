package com.example.rpg_manager.services;

import com.example.rpg_manager.model.Cenario;
import com.example.rpg_manager.repository.CenarioRepository;
import javafx.collections.ObservableList;

public class CenarioService {

    private final CenarioRepository repository =
            new CenarioRepository();


    public ObservableList<Cenario> listar() {

        return repository.listar();
    }


    public Cenario buscarPorId(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException(
                    "ID do cenário não pode ser nulo."
            );
        }

        return repository.buscarPorId(id);
    }


    public void salvar(Cenario cenario) {

        validar(cenario);

        repository.salvar(cenario);
    }


    public void atualizar(Cenario cenario) {

        validar(cenario);

        if (cenario.getId() == null) {

            throw new IllegalArgumentException(
                    "Não é possível atualizar um cenário sem ID."
            );
        }

        repository.atualizar(cenario);
    }


    public void excluir(Integer id) {

        if (id == null) {

            throw new IllegalArgumentException(
                    "ID do cenário não pode ser nulo."
            );
        }

        repository.excluir(id);
    }


    private void validar(Cenario cenario) {

        if (cenario == null) {

            throw new IllegalArgumentException(
                    "Cenário não pode ser nulo."
            );
        }

        if (cenario.getNome() == null
                || cenario.getNome().isBlank()) {

            throw new IllegalArgumentException(
                    "O cenário precisa ter um nome."
            );
        }

        if (cenario.getImagem() == null
                || cenario.getImagem().isBlank()) {

            throw new IllegalArgumentException(
                    "O cenário precisa ter uma imagem."
            );
        }
    }
}