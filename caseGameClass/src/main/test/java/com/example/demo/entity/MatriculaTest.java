package entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatriculaTest {

    // --- 1. Testes de Sucesso (Caminho Feliz) ---

    @Test
    @DisplayName("Deve criar Matrícula com 6 dígitos válidos")
    void deveCriarMatriculaComSeisDigitos() {
        // Dado (Given)
        String raValido = "123456";

        // Quando (When)
        Matricula matricula = new Matricula(raValido);

        // Então (Then)
        assertNotNull(matricula);
        assertEquals(raValido, matricula.getRa());
    }

    // --- 2. Testes de Falha ---

    @Test
    @DisplayName("Não deve criar Matrícula com RA nulo")
    void naoDeveCriarMatriculaComRaNulo() {
        // Dado
        String raNulo = null;

        // Quando & Então
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            new Matricula(raNulo);
        });

        assertEquals("RA inválido. Deve conter exatamente 6 números.", excecao.getMessage());
    }

    @Test
    @DisplayName("Não deve criar Matrícula com menos de 6 dígitos")
    void naoDeveCriarMatriculaComMenosDeSeisDigitos() {
        // Dado
        String raCurto = "12345"; // 5 dígitos

        // Quando & Então
        assertThrows(IllegalArgumentException.class, () -> {
            new Matricula(raCurto);
        });
    }

    @Test
    @DisplayName("Não deve criar Matrícula com mais de 6 dígitos")
    void naoDeveCriarMatriculaComMaisDeSeisDigitos() {
        // Dado
        String raLongo = "1234567"; // 7 dígitos

        // Quando & Então
        assertThrows(IllegalArgumentException.class, () -> {
            new Matricula(raLongo);
        });
    }

    @Test
    @DisplayName("Não deve criar Matrícula com letras")
    void naoDeveCriarMatriculaComLetras() {
        // Dado
        String raComLetras = "12345a";

        // Quando & Então
        assertThrows(IllegalArgumentException.class, () -> {
            new Matricula(raComLetras);
        });
    }

    @Test
    @DisplayName("Não deve criar Matrícula com string vazia")
    void naoDeveCriarMatriculaComStringVazia() {
        // Dado
        String raVazio = "";

        // Quando & Então
        assertThrows(IllegalArgumentException.class, () -> {
            new Matricula(raVazio);
        });
    }

    // --- 3. Testes de Comportamento (Equals e HashCode) ---

    @Test
    @DisplayName("Duas Matrículas com mesmo número devem ser iguais")
    void deveConfirmarIgualdadeDeMatriculas() {
        // Dado
        Matricula matricula1 = new Matricula("112233");
        Matricula matricula2 = new Matricula("112233");

        // Quando & Então
        assertTrue(matricula1.equals(matricula2));
        assertTrue(matricula2.equals(matricula1));
        assertEquals(matricula1.hashCode(), matricula2.hashCode());
    }

    @Test
    @DisplayName("Duas Matrículas com números diferentes não devem ser iguais")
    void deveConfirmarDiferencaDeMatriculas() {
        // Dado
        Matricula matricula1 = new Matricula("112233");
        Matricula matricula2 = new Matricula("445566");

        // Quando & Então
        assertFalse(matricula1.equals(matricula2));
        assertFalse(matricula2.equals(matricula1));
    }

    @Test
    @DisplayName("Matrícula não deve ser igual a nulo ou outro tipo de objeto")
    void matriculaNaoDeveSerIgualANuloOuOutroObjeto() {
        // Dado
        Matricula matricula = new Matricula("123456");

        // Quando & Então
        assertFalse(matricula.equals(null));
        assertFalse(matricula.equals("123456")); // Comparando com uma String
    }

    // --- 4. Teste do Método toString() ---

    @Test
    @DisplayName("toString() deve retornar o número do RA")
    void toStringDeveRetornarNumeroDoRa() {
        // Dado
        String raValido = "987654";
        Matricula matricula = new Matricula(raValido);

        // Quando & Então
        assertEquals(raValido, matricula.toString());
    }
}