package com.example.rpg_manager.services;

import com.example.rpg_manager.model.Personagem;

public class CalculadoraStatus {

    private static int faixaNex(Personagem p) {
        return p.getNex() / 12;
    }

    public static int calcularVidaMaxima(Personagem p) {

        int base =
                25 + (p.getVigor() * 6);

        int ganhoPorFaixa =
                8 + (p.getVigor() * 2);

        return base
                + (faixaNex(p) * ganhoPorFaixa);
    }

    public static int calcularPeMaximo(Personagem p) {

        int base =
                15 + (p.getPresenca() * 4);

        int ganhoPorFaixa =
                5 + p.getPresenca();

        return base
                + (faixaNex(p) * ganhoPorFaixa);
    }

    public static int calcularSanidadeMaxima(Personagem p) {

        int base =
                20 + (p.getIntelecto() * 5);

        int ganhoPorFaixa =
                5 + p.getIntelecto();

        return base
                + (faixaNex(p) * ganhoPorFaixa);
    }

    public static int calcularDefesa(Personagem p) {

        return 10
                + (p.getAgilidade() * 2)
                + faixaNex(p);
    }

    public static int calcularEsquiva(Personagem p) {

        return calcularDefesa(p)
                + p.getAgilidade()
                + (faixaNex(p) / 2);
    }

    public static int calcularBloqueio(Personagem p) {

        return 5
                + p.getForca()
                + faixaNex(p);
    }

    public static int calcularDeslocamento(Personagem p) {

        return 9
                + p.getAgilidade();
    }

    public static int calcularLimitePeTurno(Personagem p) {

        return 2
                + (faixaNex(p) * 2);
    }
}