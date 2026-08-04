package com.example.rpg_manager.model;
import com.example.rpg_manager.services.CalculadoraStatus;

import java.util.ArrayList;
import java.util.List;

public class Personagem {

    private Integer id;

    // Básico
    private String nome;
    private String avatar;
    private Classes classe;
    private int nex;

    // Atributos
    private int agilidade;
    private int forca;
    private int intelecto;
    private int presenca;
    private int vigor;

    private int pontosDisponiveis = 10;

    // Status atuais
    private int vidaAtual;
    private int peAtual;
    private int sanidadeAtual;

    private int rodadasMorrendo;
    private int trilhaProgresso;

    private List<Condicao> condicoes = new ArrayList<>();
    private List<Pericia> pericias = new ArrayList<>();
    private List<Equipamento> equipamentos = new ArrayList<>();
    private List<Habilidade> habilidades = new ArrayList<>();

    public Personagem() {

        this.condicoes = new ArrayList<>();
        this.pericias = new ArrayList<>();
        this.equipamentos = new ArrayList<>();
        this.habilidades = new ArrayList<>();
    }

    public void setValorAtributo(Atributo atributo, int valor) {

        switch (atributo) {

            case AGILIDADE -> agilidade = valor;
            case FORCA -> forca = valor;
            case INTELECTO -> intelecto = valor;
            case PRESENCA -> presenca = valor;
            case VIGOR -> vigor = valor;

        }
    }

    public void restaurarVida() {
        this.vidaAtual = getVidaMaxima();
    }

    public void restaurarPe() {
        this.peAtual = getPeMaximo();
    }

    public void restaurarSanidade() {
        this.sanidadeAtual = getSanidadeMaxima();
    }

    public int getVidaMaxima() {
        return CalculadoraStatus.calcularVidaMaxima(this);
    }

    public int getPeMaximo() {
        return CalculadoraStatus.calcularPeMaximo(this);
    }

    public int getSanidadeMaxima() {
        return CalculadoraStatus.calcularSanidadeMaxima(this);
    }

    public int getDefesa() {
        return CalculadoraStatus.calcularDefesa(this);
    }

    public int getEsquiva() {
        return CalculadoraStatus.calcularEsquiva(this);
    }

    public int getBloqueio() {
        return CalculadoraStatus.calcularBloqueio(this);
    }

    public int getDeslocamento() {
        return CalculadoraStatus.calcularDeslocamento(this);
    }

    public int getLimitePePorTurno() {
        return CalculadoraStatus.calcularLimitePeTurno(this);
    }

    public int getPontosDisponiveis() {
        return pontosDisponiveis;
    }

    public void setPontosDisponiveis(int pontosDisponiveis) {
        this.pontosDisponiveis = pontosDisponiveis;
    }

    public int getValorAtributo(Atributo atributo) {

        return switch (atributo) {
            case AGILIDADE -> agilidade;
            case FORCA -> forca;
            case INTELECTO -> intelecto;
            case PRESENCA -> presenca;
            case VIGOR -> vigor;
        };
    }

    public void recuperarTodosStatus() {
        vidaAtual = getVidaMaxima();
        peAtual = getPeMaximo();
        sanidadeAtual = getSanidadeMaxima();
        rodadasMorrendo = 0;
    }

    public int getPontosRestantes() {
        return pontosDisponiveis -
                (agilidade + forca + intelecto + presenca + vigor);
    }

//    public Integer getNivel() {
//        return nivel;
//    }
//
//    public void setNivel(Integer nivel) {
//        this.nivel = nivel;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Classes getClasse() {
        return classe;
    }

    public void setClasse(Classes classe) {
        this.classe = classe;
    }

    public int getNex() {
        return nex;
    }

    public void setNex(int nex) {
        this.nex = nex;
    }

    public int getAgilidade() {
        return agilidade;
    }

    public void setAgilidade(int agilidade) {
        this.agilidade = agilidade;
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public int getIntelecto() {
        return intelecto;
    }

    public void setIntelecto(int intelecto) {
        this.intelecto = intelecto;
    }

    public int getPresenca() {
        return presenca;
    }

    public void setPresenca(int presenca) {
        this.presenca = presenca;
    }

    public int getVigor() {
        return vigor;
    }

    public void setVigor(int vigor) {
        this.vigor = vigor;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public int getPeAtual() {
        return peAtual;
    }

    public void setPeAtual(int peAtual) {
        this.peAtual = peAtual;
    }

    public int getSanidadeAtual() {
        return sanidadeAtual;
    }

    public void setSanidadeAtual(int sanidadeAtual) {
        this.sanidadeAtual = sanidadeAtual;
    }

    public int getRodadasMorrendo() {
        return rodadasMorrendo;
    }

    public void setRodadasMorrendo(int rodadasMorrendo) {
        this.rodadasMorrendo = rodadasMorrendo;
    }

    public int getTrilhaProgresso() {
        return trilhaProgresso;
    }

    public void setTrilhaProgresso(int trilhaProgresso) {
        this.trilhaProgresso = trilhaProgresso;
    }

    public List<Condicao> getCondicoes() {
        return condicoes;
    }

    public void setCondicoes(List<Condicao> condicoes) {
        this.condicoes = condicoes;
    }

    public List<Pericia> getPericias() {
        return pericias;
    }

    public void setPericias(List<Pericia> pericias) {
        this.pericias = pericias;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public List<Habilidade> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidade> habilidades) {
        this.habilidades = habilidades;
    }

    @Override
    public String toString() {
        return "Personagem{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", avatar='" + avatar + '\'' +
                ", classe=" + classe +
                ", nex=" + nex +
                ", agilidade=" + agilidade +
                ", forca=" + forca +
                ", intelecto=" + intelecto +
                ", presenca=" + presenca +
                ", vigor=" + vigor +
                ", vidaAtual=" + vidaAtual +
                ", peAtual=" + peAtual +
                ", sanidadeAtual=" + sanidadeAtual +
                ", rodadasMorrendo=" + rodadasMorrendo +
                ", trilhaProgresso=" + trilhaProgresso +
                ", condicoes=" + condicoes +
                ", pericias=" + pericias +
                ", equipamentos=" + equipamentos +
                ", habilidades=" + habilidades +
                '}';
    }
}
