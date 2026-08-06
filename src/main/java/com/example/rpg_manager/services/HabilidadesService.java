package com.example.rpg_manager.services;

import com.example.rpg_manager.model.Habilidade;
import com.example.rpg_manager.repository.HabilidadesRepository;
import javafx.collections.ObservableList;

public class HabilidadesService {

    private final HabilidadesRepository repository = new HabilidadesRepository();

    public void salvar(Habilidade habilidade){

        if (habilidade.getNome().isBlank()){
            throw new IllegalArgumentException("Insira um nome");
        }

        repository.salvar(habilidade);
    }

    public ObservableList<Habilidade> listar() {return repository.listar();}

    public void atualizar(Habilidade habilidade) { repository.atualizar(habilidade);}

    public void excluir(Integer id) {repository.excluir(id);}
}
