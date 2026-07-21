package com.example.rpg_manager.model;

import javafx.scene.control.Spinner;

public class Personagem {
    private String nome;
    private int nivel;
    private Classes classe;

    public Personagem(String nome, int nivel, Classes classe) {
        this.nome = nome;
        this.nivel = nivel;
        this.classe = classe;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Classes getClasse() {
        return classe;
    }

    public void setClasse(Classes classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return "Personagem{" +
                "nome='" + nome + '\'' +
                ", nivel=" + nivel +
                ", classe=" + classe +
                '}';
    }
}
