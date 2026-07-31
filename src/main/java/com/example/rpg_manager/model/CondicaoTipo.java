package com.example.rpg_manager.model;

public enum CondicaoTipo {

    ABALADO("Abalado"),
    AGARRADO("Agarrado"),
    ALQUEBRADO("Alquebrado"),
    APAVORADO("Apavorado"),
    ASFIXIADO("Asfixiado"),
    ATORDOADO("Atordoado"),
    CAIDO("Caído"),
    CEGO("Cego"),
    CONFUSO("Confuso"),
    DEBILITADO("Debilitado"),
    DESPREVENIDO("Desprevenido"),
    DOENTE("Doente"),
    EM_CHAMAS("Em Chamas"),
    ENJOADO("Enjoado"),
    ENLOUQUECENDO("Enlouquecendo"),
    ENREDADO("Enredado"),
    ENVENENADO("Envenenado"),
    ESMORECIDO("Esmorecido"),
    EXAUSTO("Exausto"),
    FASCINADO("Fascinado"),
    FATIGADO("Fatigado"),
    FRACO("Fraco"),
    FRUSTRADO("Frustrado"),
    IMOVEL("Imóvel"),
    INCONSCIENTE("Inconsciente"),
    INDEFESO("Indefeso"),
    LENTO("Lento"),
    MACHUCADO("Machucado"),
    MORRENDO("Morrendo"),
    OFUSCADO("Ofuscado"),
    PARALISADO("Paralisado"),
    PASMO("Pasmo"),
    PERTURBADO("Perturbado"),
    PETRIFICADO("Petrificado"),
    SANGRANDO("Sangrando"),
    SURDO("Surdo"),
    SURPREENDIDO("Surpreendido"),
    VULNERAVEL("Vulnerável");

    private final String nome;

    CondicaoTipo(String nome) {
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