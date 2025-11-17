package com.example.demo.dto;

import com.example.demo.entity.Conteudo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConteudoDTOTest {

    @Test
    @DisplayName("Deve testar getters, setters e construtor vazio")
    void deveTestarGettersSettersEConstrutorVazio() {
        // 1. Testa Construtor Vazio
        ConteudoDTO dto = new ConteudoDTO();
        
        // 2. Testa Setters
        dto.setNome("Aula Teste");
        dto.setUrlVideo("http://teste.com");

        // 3. Testa Getters
        assertEquals("Aula Teste", dto.getNome());
        assertEquals("http://teste.com", dto.getUrlVideo());
    }

    @Test
    @DisplayName("Deve testar construtor com argumentos")
    void deveTestarConstrutorComArgumentos() {
        String nome = "Aula Cheia";
        String url = "http://full.com";

        ConteudoDTO dto = new ConteudoDTO(nome, url);

        assertEquals(nome, dto.getNome());
        assertEquals(url, dto.getUrlVideo());
    }

    @Test
    @DisplayName("Deve converter de Entidade para DTO")
    void deveConverterEntidadeParaDTO() {
        // Cria a entidade e popula usando os setters que criamos anteriormente
        Conteudo entity = new Conteudo();
        entity.setNome("Nome da Entidade");
        entity.setUrlVideo("http://entidade.com");

        // Testa o construtor que aceita a Entidade
        ConteudoDTO dto = new ConteudoDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getNome(), dto.getNome());
        assertEquals(entity.getUrlVideo(), dto.getUrlVideo());
    }
}