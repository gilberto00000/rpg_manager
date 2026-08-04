package com.example.rpg_manager.model;

public class Pericia {

    private Integer id;
    private PericiaTipo tipo;
    private boolean treinado;
    private int bonusManual;

    public Pericia() {
    }

    public Pericia(PericiaTipo tipo) {
        this.tipo = tipo;
        this.treinado = false;
        this.bonusManual = 0;
    }

    public int calcularBonus(Personagem personagem) {

        int atributo = personagem.getValorAtributo(
                tipo.getAtributoBase()
        );

        int treinamento = treinado ? 5 : 0;

        return atributo + treinamento + bonusManual;
    }

    // getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PericiaTipo getTipo() {
        return tipo;
    }

    public void setTipo(PericiaTipo tipo) {
        this.tipo = tipo;
    }

    public boolean isTreinado() {
        return treinado;
    }

    public void setTreinado(boolean treinado) {
        this.treinado = treinado;
    }

    public int getBonusManual() {
        return bonusManual;
    }

    public void setBonusManual(int bonusManual) {
        this.bonusManual = bonusManual;
    }
}