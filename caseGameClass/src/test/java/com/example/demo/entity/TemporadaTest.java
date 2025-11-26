package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TemporadaTest {

    @Test
    @DisplayName("Deve criar temporada válida")
    void deveCriarTemporadaValida() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.plusDays(30);
        
        Temporada temporada = new Temporada("Verão 2025", "Desc", inicio, fim);
        
        assertNotNull(temporada);
        assertEquals("Verão 2025", temporada.getNome());
        assertTrue(temporada.isAtiva());
    }

    @Test
    @DisplayName("Não deve criar temporada com data fim antes do início")
    void naoDeveCriarComDatasInvalidas() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.minusDays(1); // Ontem

        assertThrows(IllegalArgumentException.class, () -> {
            new Temporada("Inválida", "Desc", inicio, fim);
        });
    }

    @Test
    @DisplayName("Deve encerrar temporada")
    void deveEncerrarTemporada() {
        Temporada temporada = new Temporada("Teste", "Desc", LocalDate.now(), LocalDate.now().plusDays(1));
        temporada.encerrarTemporada();
        assertFalse(temporada.isAtiva());
    }
}