package com.example.rpg_manager.services;

import com.example.rpg_manager.model.Personagem;

public class CalculadoraStatus {

    public static int calcularVidaMaxima(Personagem p) {
        return 20 + (p.getVigor() * 5);
    }

    public static int calcularPeMaximo(Personagem p) {
        return 5 + (p.getIntelecto() * 3);
    }

    public static int calcularSanidadeMaxima(Personagem p) {
        return 10 + (p.getPresenca() * 4);
    }

    public static int calcularDefesa(Personagem p) {
        return 10 + p.getAgilidade();
    }

    public static int calcularEsquiva(Personagem p) {
        return 5 + p.getAgilidade();
    }

    public static int calcularBloqueio(Personagem p) {
        return 5 + p.getForca();
    }

    public static int calcularDeslocamento(Personagem p) {
        return 9 + p.getAgilidade();
    }

    public static int calcularLimitePeTurno(Personagem p) {
        return Math.max(1, p.getNex() / 10);
    }
}