package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestaoTest {

    // --- Helper para criar lista de opções ---
    private List<Opcao> criarOpcoes(int quantidade, int corretas) {
        List<Opcao> opcoes = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            // Se ainda precisamos gerar opções corretas, marca como true
            boolean ehCorreta = i < corretas;
            opcoes.add(new Opcao("Opção " + i, ehCorreta));
        }
        return opcoes;
    }

    // --- Testes de Sucesso ---

    @Test
    @DisplayName("Deve criar Questão válida (5 opções, 1 correta)")
    void deveCriarQuestaoValida() {
        List<Opcao> opcoesValidas = criarOpcoes(5, 1);

        assertDoesNotThrow(() -> {
            new Questao("Enunciado Válido", 10, opcoesValidas);
        });
    }

    // --- Testes de Falha (Quantidade de Opções) ---

    @Test
    @DisplayName("Não deve criar Questão com 4 opções")
    void naoDeveCriarComMenosDeCincoOpcoes() {
        List<Opcao> opcoes = criarOpcoes(4, 1); // 4 opções, 1 correta

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Questao("Enunciado", 10, opcoes);
        });
        assertEquals("Uma questão deve conter exatamente 5 opções.", ex.getMessage());
    }

    @Test
    @DisplayName("Não deve criar Questão com 6 opções")
    void naoDeveCriarComMaisDeCincoOpcoes() {
        List<Opcao> opcoes = criarOpcoes(6, 1); // 6 opções

        assertThrows(IllegalArgumentException.class, () -> {
            new Questao("Enunciado", 10, opcoes);
        });
    }

    // --- Testes de Falha (Regra da Opção Correta) ---

    @Test
    @DisplayName("Não deve criar Questão com 0 opções corretas")
    void naoDeveCriarSemOpcaoCorreta() {
        List<Opcao> opcoes = criarOpcoes(5, 0); // 5 opções, TODAS falsas

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Questao("Enunciado", 10, opcoes);
        });
        assertEquals("A questão deve ter exatamente 1 opção correta e 4 erradas.", ex.getMessage());
    }

    @Test
    @DisplayName("Não deve criar Questão com 2 opções corretas")
    void naoDeveCriarComDuasOpcoesCorretas() {
        List<Opcao> opcoes = criarOpcoes(5, 2); // 5 opções, 2 verdadeiras

        assertThrows(IllegalArgumentException.class, () -> {
            new Questao("Enunciado", 10, opcoes);
        });
    }

    // --- Testes de Validação Básica ---

    @Test
    @DisplayName("Não deve criar Questão com enunciado vazio")
    void naoDeveCriarSemEnunciado() {
        List<Opcao> opcoes = criarOpcoes(5, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            new Questao("", 10, opcoes);
        });
    }
}