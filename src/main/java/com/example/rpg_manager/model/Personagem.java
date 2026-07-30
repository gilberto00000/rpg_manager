package com.example.rpg_manager.model;

import javafx.scene.control.Spinner;

public class Personagem {
    private Integer id;
    private String nome;
    private int nivel;
    private Classes classe;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Personagem(String nome, int nivel, Classes classe) {
        this.nome = nome;
        this.nivel = nivel;
        this.classe = classe;
    }

    public Personagem(Integer id, String nome, int nivel, Classes classe) {
        this.id = id;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
