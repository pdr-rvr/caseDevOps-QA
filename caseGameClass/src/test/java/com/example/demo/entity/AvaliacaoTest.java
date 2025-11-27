package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AvaliacaoTest {

    // --- 1. Cobre a validação do Título (o throw exception) ---

    @Test
    @DisplayName("Não deve criar Avaliação com título inválido (Nulo ou Vazio)")
    void naoDeveCriarComTituloInvalido() {
        Curso curso = mock(Curso.class);

        // Testa Null
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new Avaliacao(null, curso);
        });
        assertEquals("O título da avaliação é obrigatório.", ex1.getMessage());

        // Testa Vazio
        assertThrows(IllegalArgumentException.class, () -> {
            new Avaliacao("", curso);
        });

        // Testa Espaços em branco
        assertThrows(IllegalArgumentException.class, () -> {
            new Avaliacao("   ", curso);
        });
    }

    // --- 2. Cobre o método calcularPontuacaoMaxima ---

    @Test
    @DisplayName("Deve calcular a pontuação máxima somando as questões")
    void deveCalcularPontuacaoMaxima() {
        // Dado
        Curso curso = mock(Curso.class);
        Avaliacao avaliacao = new Avaliacao("Prova Matemática", curso);

        Questao q1 = mock(Questao.class);
        when(q1.getPontos()).thenReturn(10); // Questão vale 10

        Questao q2 = mock(Questao.class);
        when(q2.getPontos()).thenReturn(25); // Questão vale 25

        // Quando
        avaliacao.adicionarQuestao(q1);
        avaliacao.adicionarQuestao(q2);

        // Então (10 + 25 = 35)
        assertEquals(35, avaliacao.calcularPontuacaoMaxima());
    }

    @Test
    @DisplayName("Deve retornar 0 se a avaliação não tiver questões")
    void deveRetornarZeroSemQuestoes() {
        Curso curso = mock(Curso.class);
        Avaliacao avaliacao = new Avaliacao("Prova Vazia", curso);

        assertEquals(0, avaliacao.calcularPontuacaoMaxima());
    }
}