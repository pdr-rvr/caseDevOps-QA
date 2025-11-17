package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SenhaTest {

    // --- 1. Teste de Sucesso (Construtor) ---

    @Test
    @DisplayName("Deve criar Senha com hash válido")
    void deveCriarSenhaComHashValido() {
        // Dado
        String hashValido = "a1b2c3d4$hash_seguro$e5f6g7h8";

        // Quando
        Senha senha = new Senha(hashValido);

        // Então
        assertNotNull(senha);
        assertEquals(hashValido, senha.getHash());
    }

    // --- 2. Testes de Falha (Construtor) ---

    @Test
    @DisplayName("Não deve criar Senha com hash nulo")
    void naoDeveCriarSenhaComHashNulo() {
        // Quando & Então
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            new Senha(null);
        });
        assertEquals("Hash de senha não pode ser nulo ou vazio.", excecao.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "}) 
    @DisplayName("Não deve criar Senha com hash em branco ou vazio")
    void naoDeveCriarSenhaComHashEmBranco(String hashInvalido) {
        // Quando & Então
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            new Senha(hashInvalido);
        });
        assertEquals("Hash de senha não pode ser nulo ou vazio.", excecao.getMessage());
    }

    // --- 3. Testes de Comportamento (Equals e HashCode) ---

    @Test
    @DisplayName("Duas Senhas com mesmo hash devem ser iguais")
    void deveConfirmarIgualdadeDeSenhas() {
        // Dado
        String hash = "hash_igual";
        Senha senha1 = new Senha(hash);
        Senha senha2 = new Senha(hash);

        // Quando & Então
        assertTrue(senha1.equals(senha2));
        assertTrue(senha2.equals(senha1));
        assertEquals(senha1.hashCode(), senha2.hashCode());
    }

    @Test
    @DisplayName("Duas Senhas com hashes diferentes não devem ser iguais")
    void deveConfirmarDiferencaDeSenhas() {
        // Dado
        Senha senha1 = new Senha("hash_1");
        Senha senha2 = new Senha("hash_2");

        // Quando & Então
        assertFalse(senha1.equals(senha2));
    }
    
    @Test
    @DisplayName("equals() deve retornar true para a mesma instância")
    void equalsDeveRetornarTrueParaMesmaInstancia() {
        // Dado
        Senha senha = new Senha("hash_unico");

        // Quando & Então
        assertTrue(senha.equals(senha));
    }

    @Test
    @DisplayName("equals() deve retornar false para nulo ou classe diferente")
    void equalsDeveRetornarFalseParaNuloOuClasseDiferente() {
        // Dado
        Senha senha = new Senha("hash_unico");
        
        // Quando & Então
        assertFalse(senha.equals(null));
        assertFalse(senha.equals("hash_unico")); 
    }

    // --- 4. Teste do Método toString() ---

    @Test
    @DisplayName("toString() deve retornar o hash mascarado")
    void toStringDeveRetornarHashMascarado() {
        // Dado
        Senha senha = new Senha("hash_super_secreto");

        // Quando
        String resultadoToString = senha.toString();

        // Então
        assertEquals("Senha [hash=***]", resultadoToString);
    }
}