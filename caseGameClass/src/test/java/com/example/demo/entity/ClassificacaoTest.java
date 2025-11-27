package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ClassificacaoTest {

    // --- 1. Testes de Regra de Negócio (Domínio) ---

    @Test
    @DisplayName("Deve criar Classificação válida e iniciar com zero pontos")
    void deveCriarClassificacaoValida() {
        Usuario aluno = mock(Usuario.class);
        Temporada temporada = mock(Temporada.class);

        Classificacao classificacao = new Classificacao(aluno, temporada);

        assertNotNull(classificacao);
        assertEquals(aluno, classificacao.getAluno());
        assertEquals(temporada, classificacao.getTemporada());
        assertEquals(0, classificacao.getPontuacaoTotal());
        assertNotNull(classificacao.getUltimaAtualizacao());
        // O ID é nulo antes de persistir, isso é normal e conta como cobertura do getter
        assertNull(classificacao.getId()); 
    }

    @Test
    @DisplayName("Construtor deve falhar se parâmetros forem nulos")
    void construtorDeveValidarParametros() {
        Usuario aluno = mock(Usuario.class);
        Temporada temporada = mock(Temporada.class);

        // Testa aluno nulo
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new Classificacao(null, temporada);
        });
        assertEquals("Aluno e Temporada são obrigatórios.", ex1.getMessage());

        // Testa temporada nula
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            new Classificacao(aluno, null);
        });
        assertEquals("Aluno e Temporada são obrigatórios.", ex2.getMessage());
    }

    @Test
    @DisplayName("Deve somar pontos corretamente")
    void deveSomarPontos() {
        Usuario aluno = mock(Usuario.class);
        Temporada temporada = mock(Temporada.class);
        
        Classificacao classificacao = new Classificacao(aluno, temporada);

        classificacao.adicionarPontos(100);
        assertEquals(100, classificacao.getPontuacaoTotal());

        classificacao.adicionarPontos(50);
        assertEquals(150, classificacao.getPontuacaoTotal());
    }
    
    @Test
    @DisplayName("Não deve somar pontos inválidos (Nulo, Zero ou Negativo)")
    void naoDeveSomarPontosInvalidos() {
        Usuario aluno = mock(Usuario.class);
        Temporada temporada = mock(Temporada.class);
        Classificacao classificacao = new Classificacao(aluno, temporada);

        // Testa Nulo
        classificacao.adicionarPontos(null);
        assertEquals(0, classificacao.getPontuacaoTotal());

        // Testa Zero
        classificacao.adicionarPontos(0);
        assertEquals(0, classificacao.getPontuacaoTotal());

        // Testa Negativo
        classificacao.adicionarPontos(-10);
        assertEquals(0, classificacao.getPontuacaoTotal());
    }

    // --- 2. Testes Estruturais (Lombok - Equals, HashCode, ToString) ---
    // Estes testes são essenciais para cobrir as branches geradas pelo Lombok

    @Test
    @DisplayName("Deve cobrir todos os ramos do Equals e HashCode")
    void testeEqualsHashCodeCompleto() {
        Usuario u1 = mock(Usuario.class);
        Usuario u2 = mock(Usuario.class); // Diferente de u1
        
        Temporada t1 = mock(Temporada.class);
        Temporada t2 = mock(Temporada.class); // Diferente de t1

        // Referência
        Classificacao c1 = new Classificacao(u1, t1);
        
        // Objeto Igual (Mesmo aluno e temporada)
        Classificacao c2 = new Classificacao(u1, t1);
        
        // Objetos Diferentes
        Classificacao c3 = new Classificacao(u2, t1); // Aluno diferente
        Classificacao c4 = new Classificacao(u1, t2); // Temporada diferente

        // 1. Identidade (mesmo ponteiro na memória)
        assertTrue(c1.equals(c1));

        // 2. Igualdade de valores
        assertTrue(c1.equals(c2));
        assertEquals(c1.hashCode(), c2.hashCode());

        // 3. Diferença de Tipo
        assertFalse(c1.equals(new Object()));

        // 4. Nulo
        assertFalse(c1.equals(null));

        // 5. Diferença no primeiro campo da chave (Aluno)
        assertFalse(c1.equals(c3));

        // 6. Diferença no segundo campo da chave (Temporada)
        assertFalse(c1.equals(c4));
    }

    @Test
    @DisplayName("Deve cobrir o método ToString")
    void testeToString() {
        Usuario aluno = mock(Usuario.class);
        Temporada temporada = mock(Temporada.class);
        Classificacao c = new Classificacao(aluno, temporada);

        // Apenas chamamos para garantir que o código gerado pelo Lombok é executado
        assertNotNull(c.toString());
    }
}