package com.example.rpg_manager.model;

public class Condicao {

    private Integer id;
    private CondicaoTipo tipo;

    public Condicao() {
    }

    public Condicao(CondicaoTipo tipo) {
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CondicaoTipo getTipo() {
        return tipo;
    }

    public void setTipo(CondicaoTipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo.toString();
    }
}