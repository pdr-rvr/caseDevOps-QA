package com.example.demo.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioResponseDTOTest {

    private UsuarioResponseDTO dto1;
    private UsuarioResponseDTO dto2;

    @BeforeEach
    void setUp() {
        dto1 = new UsuarioResponseDTO();
        dto1.setId(1L);
        dto1.setNome("Usuario Um");
        dto1.setEmail("usuario1@email.com");
        dto1.setMatricula("111111");

        dto2 = new UsuarioResponseDTO();
        dto2.setId(1L);
        dto2.setNome("Usuario Um"); 
        dto2.setEmail("usuario1@email.com");
        dto2.setMatricula("111111"); 
    }

    @Test
    @DisplayName("equals() deve retornar true para a mesma instância")
    void equalsDeveRetornarTrueParaMesmaInstancia() {
        assertTrue(dto1.equals(dto1));
    }

    @Test
    @DisplayName("equals() deve retornar true para objetos com mesmo ID e Matrícula")
    void equalsDeveRetornarTrueParaObjetosIguais() {
        assertTrue(dto1.equals(dto2));
        assertTrue(dto2.equals(dto1));
    }

    @Test
    @DisplayName("hashCode() deve ser igual para objetos iguais")
    void hashCodeDeveSerIgualParaObjetosIguais() {
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("equals() deve retornar false se o ID for diferente")
    void equalsDeveRetornarFalseParaIdDiferente() {
        dto2.setId(2L); 
        assertFalse(dto1.equals(dto2));
    }

    @Test
    @DisplayName("hashCode() deve ser diferente se o ID for diferente")
    void hashCodeDeveSerDiferenteParaIdDiferente() {
        dto2.setId(2L);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("equals() deve retornar false se a Matrícula for diferente")
    void equalsDeveRetornarFalseParaMatriculaDiferente() {
        dto2.setMatricula("222222"); 
        assertFalse(dto1.equals(dto2));
    }

    @Test
    @DisplayName("hashCode() deve ser diferente se a Matrícula for diferente")
    void hashCodeDeveSerDiferenteParaMatriculaDiferente() {
        dto2.setMatricula("222222");
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("equals() deve retornar false para nulo")
    void equalsDeveRetornarFalseParaNulo() {
        assertFalse(dto1.equals(null));
    }

    @Test
    @DisplayName("equals() deve retornar false para classe diferente")
    void equalsDeveRetornarFalseParaClasseDiferente() {
        assertFalse(dto1.equals("uma string"));
    }

    @Test
    @DisplayName("Construtor padrão deve funcionar (cobertura)")
    void deveCobrirConstrutorPadrao() {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        assertTrue(dto instanceof UsuarioResponseDTO);
    }
}