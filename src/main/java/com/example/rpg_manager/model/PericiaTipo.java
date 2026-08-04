package com.example.rpg_manager.model;

public enum PericiaTipo {



    ACROBACIA("Acrobacia", Atributo.AGILIDADE),
    ADESTRAMENTO("Adestramento", Atributo.PRESENCA),
    ARTES("Artes", Atributo.PRESENCA),
    ATLETISMO("Atletismo", Atributo.FORCA),
    ATUALIDADES("Atualidades", Atributo.INTELECTO),
    CIENCIAS("Ciências", Atributo.INTELECTO),
    ENGANACAO("Enganação", Atributo.PRESENCA),
    FORTITUDE("Fortitude", Atributo.VIGOR),
    FURTIVIDADE("Furtividade", Atributo.AGILIDADE),
    INICIATIVA("Iniciativa", Atributo.AGILIDADE),
    INTIMIDACAO("Intimidação", Atributo.PRESENCA),
    INTUICAO("Intuição", Atributo.PRESENCA),
    INVESTIGACAO("Investigação", Atributo.INTELECTO),
    LUTA("Luta", Atributo.FORCA),
    MEDICINA("Medicina", Atributo.INTELECTO),
    OCULTISMO("Ocultismo", Atributo.INTELECTO),
    PERCEPCAO("Percepção", Atributo.PRESENCA),
    PILOTAGEM("Pilotagem", Atributo.AGILIDADE),
    PONTARIA("Pontaria", Atributo.AGILIDADE),
    PROFISSAO("Profissão", Atributo.INTELECTO),
    REFLEXOS("Reflexos", Atributo.AGILIDADE),
    RELIGIAO("Religião", Atributo.PRESENCA),
    SOBREVIVENCIA("Sobrevivência", Atributo.INTELECTO),
    TATICA("Tática", Atributo.INTELECTO),
    VONTADE("Vontade", Atributo.PRESENCA);

    private String nome;
    private Atributo atributoBase;

    PericiaTipo(String nome, Atributo atributoBase) {
        this.nome = nome;
        this.atributoBase = atributoBase;
    }

    // construtor, getters e toString()


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Atributo getAtributoBase() {
        return atributoBase;
    }

    public void setAtributoBase(Atributo atributoBase) {
        this.atributoBase = atributoBase;
    }

    @Override
    public String toString() {
        return nome;
    }
}
