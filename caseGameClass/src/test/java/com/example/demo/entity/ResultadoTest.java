package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ResultadoTest {

    // --- 1. Teste do Caminho Feliz ---
    @Test
    @DisplayName("Deve criar um Resultado válido")
    void deveCriarResultadoValido() {
        Usuario aluno = mock(Usuario.class);
        Avaliacao avaliacao = mock(Avaliacao.class);
        Temporada temporada = mock(Temporada.class);

        Resultado resultado = new Resultado(aluno, avaliacao, temporada, 100);

        assertNotNull(resultado);
        assertEquals(aluno, resultado.getAluno());
        assertEquals(100, resultado.getNotaObtida());
        assertNotNull(resultado.getDataRealizacao()); // Garante que a data foi gerada no construtor
        assertNull(resultado.getId()); // ID é nulo antes de persistir
    }

    // --- 2. Cobertura da Validação de DEPENDÊNCIAS NULAS ---
    @Test
    @DisplayName("Não deve criar Resultado com dependências nulas")
    void naoDeveCriarComDependenciasNulas() {
        Usuario aluno = mock(Usuario.class);
        Avaliacao avaliacao = mock(Avaliacao.class);
        Temporada temporada = mock(Temporada.class);

        String msgEsperada = "Aluno, Avaliação e Temporada são obrigatórios.";

        // 1. Aluno Nulo
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new Resultado(null, avaliacao, temporada, 10);
        });
        assertEquals(msgEsperada, ex1.getMessage());

        // 2. Avaliação Nula
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            new Resultado(aluno, null, temporada, 10);
        });
        assertEquals(msgEsperada, ex2.getMessage());

        // 3. Temporada Nula
        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> {
            new Resultado(aluno, avaliacao, null, 10);
        });
        assertEquals(msgEsperada, ex3.getMessage());
    }

    // --- 3. Cobertura da Validação de NOTA ---
    @Test
    @DisplayName("Não deve criar Resultado com nota inválida")
    void naoDeveCriarComNotaInvalida() {
        Usuario aluno = mock(Usuario.class);
        Avaliacao avaliacao = mock(Avaliacao.class);
        Temporada temporada = mock(Temporada.class);

        String msgEsperada = "A nota não pode ser negativa.";

        // 1. Nota Nula
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new Resultado(aluno, avaliacao, temporada, null);
        });
        assertEquals(msgEsperada, ex1.getMessage());

        // 2. Nota Negativa
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            new Resultado(aluno, avaliacao, temporada, -1);
        });
        assertEquals(msgEsperada, ex2.getMessage());
    }

    // --- 4. Cobertura Estrutural (Lombok) ---
    @Test
    @DisplayName("Deve cobrir métodos gerados pelo Lombok (Equals/ToString)")
    void testeMetodosLombok() {
        Usuario aluno = mock(Usuario.class);
        Avaliacao avaliacao = mock(Avaliacao.class);
        Temporada temporada = mock(Temporada.class);

        Resultado r1 = new Resultado(aluno, avaliacao, temporada, 10);
        Resultado r2 = new Resultado(aluno, avaliacao, temporada, 10);

        
        r1.equals(r2);
        r1.hashCode();
        assertNotNull(r1.toString());
    }
}