package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class QuestaoTest {

    // --- Helper ---
    private List<Opcao> criarOpcoes(int quantidade, int corretas) {
        List<Opcao> opcoes = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            boolean ehCorreta = i < corretas;
            opcoes.add(new Opcao("Opção " + i, ehCorreta));
        }
        return opcoes;
    }

    // --- 1. Testes de Sucesso ---
    @Test
    @DisplayName("Deve criar Questão válida")
    void deveCriarQuestaoValida() {
        List<Opcao> opcoesValidas = criarOpcoes(5, 1);
        Questao q = new Questao("Enunciado Válido", 10, opcoesValidas);
        
        assertNotNull(q);
        assertEquals("Enunciado Válido", q.getEnunciado());
        assertEquals(10, q.getPontos());
        assertEquals(5, q.getOpcoes().size());
        
        // Cobertura do setter de Avaliacao
        Avaliacao av = mock(Avaliacao.class);
        q.setAvaliacao(av);
        assertEquals(av, q.getAvaliacao());
        // Cobertura do getter de ID (null antes de persistir)
        assertNull(q.getId());
    }

    // --- 2. Cobertura de Validações Básicas (Null/Empty) ---
    
    @Test
    @DisplayName("Deve falhar com parâmetros nulos")
    void deveFalharComParametrosNulos() {
        List<Opcao> opcoes = criarOpcoes(5, 1);

        // Enunciado Nulo
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> 
            new Questao(null, 10, opcoes));
        assertEquals("O enunciado da questão é obrigatório.", ex1.getMessage());

        // Pontos Nulo
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> 
            new Questao("E", null, opcoes));
        assertEquals("A pontuação deve ser maior que zero.", ex2.getMessage());

        // Opções Nulo
        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> 
            new Questao("E", 10, null));
        assertEquals("Uma questão deve conter exatamente 5 opções.", ex3.getMessage());
    }

    @Test
    @DisplayName("Deve falhar com enunciado vazio/branco")
    void deveFalharComEnunciadoVazio() {
        List<Opcao> opcoes = criarOpcoes(5, 1);
        assertThrows(IllegalArgumentException.class, () -> new Questao("", 10, opcoes));
        assertThrows(IllegalArgumentException.class, () -> new Questao("   ", 10, opcoes));
    }

    @Test
    @DisplayName("Deve falhar com pontuação zero ou negativa")
    void deveFalharComPontuacaoInvalida() {
        List<Opcao> opcoes = criarOpcoes(5, 1);
        
        // Zero
        assertThrows(IllegalArgumentException.class, () -> new Questao("E", 0, opcoes));
        
        // Negativo
        assertThrows(IllegalArgumentException.class, () -> new Questao("E", -5, opcoes));
    }

    // --- 3. Cobertura da Regra de Negócio (Quantidade Opções) ---

    @Test
    @DisplayName("Não deve criar com != 5 opções")
    void validaQuantidadeOpcoes() {
        // Menos
        assertThrows(IllegalArgumentException.class, () -> 
            new Questao("E", 10, criarOpcoes(4, 1)));
            
        // Mais
        assertThrows(IllegalArgumentException.class, () -> 
            new Questao("E", 10, criarOpcoes(6, 1)));
    }

    @Test
    @DisplayName("Deve validar quantidade de corretas")
    void validaQuantidadeCorretas() {
        // Nenhuma correta
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> 
            new Questao("E", 10, criarOpcoes(5, 0)));
        assertEquals("A questão deve ter exatamente 1 opção correta e 4 erradas.", ex1.getMessage());

        // Duas corretas
        assertThrows(IllegalArgumentException.class, () -> 
            new Questao("E", 10, criarOpcoes(5, 2)));
    }

    // --- 4. Cobertura de Métodos Gerados (Lombok) ---
    
    @Test
    @DisplayName("Deve cobrir Equals, HashCode e ToString")
    void testeMetodosLombok() {
        List<Opcao> op = criarOpcoes(5, 1);
        Questao q1 = new Questao("Teste", 10, op);
        Questao q2 = new Questao("Teste", 10, op); // Igual a q1 (mesmo enunciado)
        Questao q3 = new Questao("Outro", 10, op); // Diferente

        // Equals
        assertTrue(q1.equals(q2));
        assertFalse(q1.equals(q3));
        assertFalse(q1.equals(null));
        assertFalse(q1.equals(new Object()));
        
        // HashCode
        assertEquals(q1.hashCode(), q2.hashCode());
        
        // ToString
        assertNotNull(q1.toString());
    }
}