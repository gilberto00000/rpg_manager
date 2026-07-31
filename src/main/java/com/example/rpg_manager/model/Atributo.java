package com.example.rpg_manager.model;

public enum Atributo {

    AGILIDADE("Agilidade"),
    FORCA("Força"),
    INTELECTO("Intelecto"),
    PRESENCA("Presença"),
    VIGOR("Vigor");

    private final String nome;

    Atributo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}