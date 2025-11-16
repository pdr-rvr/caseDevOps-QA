package entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailTest {

    // --- 1. Testes de Sucesso ---

    @ParameterizedTest
    @ValueSource(strings = {
            "usuario@dominio.com",
            "usuario.composto@dominio.com.br",
            "usuario+123@gmail.com",
            "usuario_com_underline@outlook.com",
            "a@b.co"
    })
    @DisplayName("Deve criar Email com formatos válidos")
    void deveCriarEmailComFormatosValidos(String emailValido) {
        // Quando (When)
        Email email = new Email(emailValido);

        // Então (Then)
        assertNotNull(email);
        assertEquals(emailValido, email.getEnderecoEmail());
    }

    // --- 2. Testes de Falha ---

    @Test
    @DisplayName("Não deve criar Email com valor nulo")
    void naoDeveCriarEmailNulo() {
        // Quando & Então
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            new Email(null);
        });
        assertEquals("Email inválido", excecao.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",                      // Vazio
            "email-sem-arroba.com",  // Sem @
            "email@sem-dominio",     // Sem TLD (Top-Level Domain)
            "email @dominio.com",    // Com espaço
            "@dominio.com",          // Sem parte local
            "email@.com"             // Domínio inválido
    })
    @DisplayName("Não deve criar Email com formatos inválidos")
    void naoDeveCriarEmailComFormatosInvalidos(String emailInvalido) {
        // Quando & Então
        assertThrows(IllegalArgumentException.class, () -> {
            new Email(emailInvalido);
        });
    }

    // --- 3. Testes de Comportamento ---

    @Test
    @DisplayName("Dois Emails com mesmo endereço devem ser iguais")
    void deveConfirmarIgualdadeDeEmails() {
        // Dado
        Email email1 = new Email("teste@exemplo.com");
        Email email2 = new Email("teste@exemplo.com");

        // Quando & Então
        assertTrue(email1.equals(email2));
        assertTrue(email2.equals(email1));
        assertEquals(email1.hashCode(), email2.hashCode());

        assertEquals(email1.getEnderecoEmail(), email2.getEnderecoEmail());
    }

    @Test
    @DisplayName("Dois Emails com endereços diferentes não devem ser iguais")
    void deveConfirmarDiferencaDeEmails() {
        // Dado
        Email email1 = new Email("teste1@exemplo.com");
        Email email2 = new Email("teste2@exemplo.com");

        // Quando & Então
        assertFalse(email1.equals(email2));
    }

    @Test
    @DisplayName("toString() deve retornar o endereço de email")
    void toStringDeveRetornarEnderecoDeEmail() {
        // Dado
        String endereco = "log@teste.com";
        Email email = new Email(endereco);

        // Quando & Então
        assertEquals(endereco, email.toString());
    }

    @Test
    @DisplayName("Email não deve ser igual a nulo ou outro tipo de objeto")
    void emailNaoDeveSerIgualANuloOuOutroObjeto() {
        // Dado
        Email email = new Email("teste@exemplo.com");

        // Quando & Então
        assertFalse(email.equals(null));
        assertFalse(email.equals("teste@exemplo.com")); 
    }
}