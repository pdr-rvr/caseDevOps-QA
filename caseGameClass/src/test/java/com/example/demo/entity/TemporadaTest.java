package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TemporadaTest {

    @Test
    @DisplayName("Deve criar temporada válida e ativa")
    void deveCriarTemporadaValida() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.plusDays(30);
        
        Temporada temporada = new Temporada("Verão 2025", "Desc", inicio, fim);
        
        assertNotNull(temporada);
        assertEquals("Verão 2025", temporada.getNome());
        assertTrue(temporada.isAtiva());
        assertNull(temporada.getId()); 
    }

    @Test
    @DisplayName("Não deve criar temporada com nome nulo ou em branco")
    void naoDeveCriarComNomeInvalido() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.plusDays(10);

        // Testa Nulo
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new Temporada(null, "Desc", inicio, fim);
        });
        assertEquals("O nome da temporada é obrigatório.", ex1.getMessage());

        // Testa Vazio/Branco
        assertThrows(IllegalArgumentException.class, () -> {
            new Temporada("   ", "Desc", inicio, fim);
        });
    }

    @Test
    @DisplayName("Não deve criar temporada com datas nulas")
    void naoDeveCriarComDatasNulas() {
        LocalDate agora = LocalDate.now();

        // Testa Data Inicio Null
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new Temporada("T1", "Desc", null, agora);
        });
        assertEquals("Datas de início e fim são obrigatórias.", ex1.getMessage());

        // Testa Data Fim Null
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            new Temporada("T1", "Desc", agora, null);
        });
        assertEquals("Datas de início e fim são obrigatórias.", ex2.getMessage());
    }

    @Test
    @DisplayName("Não deve criar temporada com data fim antes do início")
    void naoDeveCriarComDataFimAnterior() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.minusDays(1); // Ontem

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Temporada("Inválida", "Desc", inicio, fim);
        });
        assertEquals("A data final não pode ser anterior à data inicial.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve encerrar temporada")
    void deveEncerrarTemporada() {
        Temporada temporada = new Temporada("Teste", "Desc", LocalDate.now(), LocalDate.now().plusDays(1));
        assertTrue(temporada.isAtiva());
        
        temporada.encerrarTemporada();
        
        assertFalse(temporada.isAtiva());
    }
}