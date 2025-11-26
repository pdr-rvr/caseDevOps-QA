package com.example.demo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.entity.Temporada;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TemporadaDTOsTest {

    @Test
    @DisplayName("TemporadaRequestDTO: Deve cobrir todos os ramos do @Data")
    void testeTemporadaRequestDTO_Completo() {
        LocalDate agora = LocalDate.now();

        // Objeto Base
        TemporadaRequestDTO dto1 = new TemporadaRequestDTO();
        dto1.setNome("Verão");
        dto1.setDescricao("Desc");
        dto1.setDataInicio(agora);
        dto1.setDataFim(agora.plusDays(10));

        // Objeto Igual (Cópia)
        TemporadaRequestDTO dto2 = new TemporadaRequestDTO();
        dto2.setNome("Verão");
        dto2.setDescricao("Desc");
        dto2.setDataInicio(agora);
        dto2.setDataFim(agora.plusDays(10));

        // Objeto Diferente (Muda um campo)
        TemporadaRequestDTO dto3 = new TemporadaRequestDTO();
        dto3.setNome("Inverno"); // Nome diferente
        dto3.setDescricao("Desc");
        dto3.setDataInicio(agora);
        dto3.setDataFim(agora.plusDays(10));

        // --- Testes de Equals (Cobrindo as Branches) ---

        // 1. Igualdade Simples (Happy Path)
        assertEquals(dto1, dto2);
        
        // 2. Consistência do HashCode
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // 3. Identidade (O mesmo objeto na memória) -> Cobre "if (o == this)"
        assertEquals(dto1, dto1);

        // 4. Desigualdade com Null -> Cobre "if (o == null)"
        assertNotEquals(dto1, null);

        // 5. Desigualdade de Tipo -> Cobre "if (getClass() != ...)"
        assertNotEquals(dto1, new Object());

        // 6. Desigualdade de Valor -> Cobre os "ifs" internos dos campos
        assertNotEquals(dto1, dto3);
        
        // --- Teste de ToString ---
        assertNotNull(dto1.toString());
        
        // --- Teste de Getters/Setters ---
        assertEquals("Verão", dto1.getNome());
        assertEquals("Desc", dto1.getDescricao());
    }

    @Test
    @DisplayName("TemporadaResponseDTO: Deve cobrir construtor de entidade e setters")
    void testeTemporadaResponseDTO() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.plusDays(30);

        // --- CENÁRIO 1: Teste do Construtor com Entidade (Já estava coberto) ---
        Temporada entity = new Temporada("Verão", "Sol", inicio, fim);
        // Como a entidade não tem setter de ID público, o ID virá nulo aqui, o que é ok para este teste
        
        TemporadaResponseDTO dtoViaEntity = new TemporadaResponseDTO(entity);
        assertEquals("Verão", dtoViaEntity.getNome());
        assertEquals("Sol", dtoViaEntity.getDescricao());
        assertEquals(inicio, dtoViaEntity.getDataInicio());
        assertEquals(fim, dtoViaEntity.getDataFim());
        assertTrue(dtoViaEntity.isAtiva());

        // --- CENÁRIO 2: Teste dos SETTERS (O que faltava no relatório) ---
        TemporadaResponseDTO dtoManual = new TemporadaResponseDTO();
        
        dtoManual.setId(99L);
        dtoManual.setNome("Inverno");
        dtoManual.setDescricao("Frio");
        dtoManual.setDataInicio(inicio);
        dtoManual.setDataFim(fim);
        dtoManual.setAtiva(false);

        // Assertions (Validam se o Get recupera o que o Set gravou)
        assertEquals(99L, dtoManual.getId());
        assertEquals("Inverno", dtoManual.getNome());
        assertEquals("Frio", dtoManual.getDescricao());
        assertEquals(inicio, dtoManual.getDataInicio());
        assertEquals(fim, dtoManual.getDataFim());
        assertFalse(dtoManual.isAtiva());
    }
}