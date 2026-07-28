package com.example.rpg_manager.repository;

import com.example.rpg_manager.model.Classes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassesRepository {

    private final List<Classes> classes = new ArrayList<>();
    private ObservableList<Classes> obsclasses;

    public ClassesRepository(){
        carregarClasses();
    }

    public void carregarClasses(){

        Classes c1  = new Classes(1,  "COMBATENTE",       "Focado em Dano e Defesa");
        Classes c2  = new Classes(2,  "ESPECIALISTA",     "Foco em pericias e suporte");
        Classes c3  = new Classes(3,  "SOBREVIVENTE",     "Arte de usar sucata/plantas para criar, item de cura ou um objeto pequeno");
        Classes c4  = new Classes(4,  "INVESTIGADOR",     "Aumento de foco e +5 natural em testes de investigação");
        Classes c5  = new Classes(5,  "MEDICO",           "Um D20 adicional em testes de suportes, a arte de achar ou criar algum item facilmente para ganhar PV");
        Classes c6  = new Classes(6,  "LAMINA EXORCISTA", "+2 em rituais com sacrificios, +3 em absorção de sangue para armas amaldiçoadas");
        Classes c7  = new Classes(7,  "RANGER",           "Atirador a longa distância, +5 em testes de tiro");
        Classes c8  = new Classes(8,  "TECNOMANCER",      "Especialista em manipulação hacker e modificação ciber");
        Classes c9  = new Classes(9,  "ENGENHEIRO",       "Faz tudo, +4 em testes de reparo de equipamentos");
        Classes c10 = new Classes(10, "MISTICO",          "Manipulador de matéria elemental");
        Classes c11 = new Classes(11, "ORADOR",           "cultista/mestre em escritas curativas ou malignas");

        classes.addAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11));

        obsclasses = FXCollections.observableArrayList(classes);

    }

    public List<Classes> listar() {
        return classes;
    }

    public Classes buscarPorId(int id) {

        for (Classes c : classes) {

            if (c.getId() == id) {
                return c;
            }

        }

        return null;
    }

}
