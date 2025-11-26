package com.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioTest {

    // --- Dados de mock válidos para usar nos testes ---
    private Email emailValido;
    private Matricula matriculaValida;
    private Senha senhaValida;
    private String nomeValido;

    @BeforeEach
    void setUp() {
        emailValido = new Email("teste@dominio.com");
        matriculaValida = new Matricula("123456");
        senhaValida = new Senha("hash$criptografado$123");
        nomeValido = "Nome de Teste";
    }

    // --- 1. Testes de Sucesso (Construtor) ---

    @Test
    @DisplayName("Deve criar um Usuário com dados válidos")
    void deveCriarUsuarioComDadosValidos() {
        // Quando
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);

        // Então
        assertNotNull(usuario);
        assertEquals(nomeValido, usuario.getNome());
        assertEquals(emailValido, usuario.getEmail());
        assertEquals(matriculaValida, usuario.getMatricula());
        assertEquals(senhaValida, usuario.getSenha());
    }

    // --- 2. Testes de Falha (Construtor) ---

    @Test
    @DisplayName("Não deve criar Usuário com nome nulo")
    void naoDeveCriarUsuarioComNomeNulo() {
        // Quando & Então
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(null, emailValido, matriculaValida, senhaValida);
        });
        assertEquals("Nome não pode ser nulo ou vazio", excecao.getMessage());
    }

    @Test
    @DisplayName("Não deve criar Usuário com nome em branco")
    void naoDeveCriarUsuarioComNomeEmBranco() {
        // Quando & Então
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("   ", emailValido, matriculaValida, senhaValida); // Nome com espaços
        });
        assertEquals("Nome não pode ser nulo ou vazio", excecao.getMessage());
    }

    // --- 3. Testes de Métodos de Domínio (Comportamento) ---

    @Test
    @DisplayName("Deve alterar o nome com sucesso")
    void deveAlterarNomeComSucesso() {
        // Dado
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);
        String novoNome = "Novo Nome";

        // Quando
        usuario.alterarNome(novoNome);

        // Então
        assertEquals(novoNome, usuario.getNome());
    }

    @Test
    @DisplayName("Não deve alterar o nome para nulo")
    void naoDeveAlterarNomeParaNulo() {
        // Dado
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);

        // Quando & Então
        assertThrows(IllegalArgumentException.class, () -> {
            usuario.alterarNome(null);
        });
        assertEquals(nomeValido, usuario.getNome()); 
    }

    @Test
    @DisplayName("Não deve alterar o nome para branco")
    void naoDeveAlterarNomeParaBranco() {
        // Dado
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);

        // Quando & Então
        assertThrows(IllegalArgumentException.class, () -> {
            usuario.alterarNome(" ");
        });
        assertEquals(nomeValido, usuario.getNome()); 
    }

    @Test
    @DisplayName("Deve alterar a senha com sucesso")
    void deveAlterarSenhaComSucesso() {
        // Dado
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);
        Senha novaSenha = new Senha("hash$nova$senha");

        // Quando
        usuario.alterarSenha(novaSenha);

        // Então
        assertEquals(novaSenha, usuario.getSenha());
    }

    @Test
    @DisplayName("Não deve alterar a senha para nulo")
    void naoDeveAlterarSenhaParaNulo() {
        // Dado
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);

        // Quando & Então
        assertThrows(NullPointerException.class, () -> {
            usuario.alterarSenha(null);
        });
        assertEquals(senhaValida, usuario.getSenha()); 
    }

    @Test
    @DisplayName("Deve manipular lista de cursos inscritos")
    void deveManipularCursosInscritos() {
        // Dado
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);
        Curso curso = new Curso("Curso Java", "Desc");

        // Quando
        usuario.inscreverEmCurso(curso);

        // Então
        assertNotNull(usuario.getCursosInscritos());
        assertTrue(usuario.getCursosInscritos().contains(curso));
        assertTrue(curso.getAlunosInscritos().contains(usuario));

        // Quando cancelar
        usuario.cancelarInscricao(curso);
        
        // Então
        assertFalse(usuario.getCursosInscritos().contains(curso));
    }

    // --- 4. Testes de Contrato (Equals e HashCode) ---

    @Test
    @DisplayName("Dois Usuários com mesma matrícula devem ser iguais")
    void deveConfirmarIgualdadeDeUsuarios() {
        // Dado
        Matricula matricula = new Matricula("999888");
        Usuario usuario1 = new Usuario("Usuario A", new Email("a@a.com"), matricula, new Senha("senha1"));
        Usuario usuario2 = new Usuario("Usuario B", new Email("b@b.com"), matricula, new Senha("senha2"));

        // Então 
        assertTrue(usuario1.equals(usuario2));
        assertTrue(usuario2.equals(usuario1));
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }

    @Test
    @DisplayName("Dois Usuários com matrículas diferentes não devem ser iguais")
    void deveConfirmarDiferencaDeUsuarios() {
        // Dado
        Usuario usuario1 = new Usuario("Usuario A", new Email("a@a.com"), new Matricula("111111"), new Senha("senha1"));
        Usuario usuario2 = new Usuario("Usuario B", new Email("b@b.com"), new Matricula("222222"), new Senha("senha2"));

        // Então
        assertFalse(usuario1.equals(usuario2));
        assertFalse(usuario2.equals(usuario1));
        assertNotEquals(usuario1.hashCode(), usuario2.hashCode());
    }

    @Test
    @DisplayName("equals() deve retornar true para a mesma instância")
    void equalsDeveRetornarTrueParaMesmaInstancia() {
        // Dado
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);

        // Quando & Então
        assertTrue(usuario.equals(usuario));
    }

    @Test
    @DisplayName("equals() deve retornar false para nulo")
    void equalsDeveRetornarFalseParaNulo() {
        // Dado
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);

        // Quando & Então
        assertFalse(usuario.equals(null));
    }

    @Test
    @DisplayName("equals() deve retornar false para classe diferente")
    void equalsDeveRetornarFalseParaClasseDiferente() {
        // Dado
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);
        Object objetoDiferente = new Object();

        // Quando & Então
        assertFalse(usuario.equals(objetoDiferente));
    }

    @Test
    @DisplayName("Deve testar Getters e Setters de ID")
    void deveTestarGetSetId() {
        Usuario usuario = new Usuario();
        usuario.setId(100L);
        
        assertEquals(100L, usuario.getId());
    }

    @Test
    @DisplayName("Deve testar Getter direto de Cursos Inscritos")
    void deveTestarGetterCursosInscritos() {
        Usuario usuario = new Usuario();
        // Garante que a lista inicializa vazia e não nula
        assertNotNull(usuario.getCursosInscritos());
        assertTrue(usuario.getCursosInscritos().isEmpty());
    }

    @Test
    @DisplayName("Deve testar o método toString para cobertura completa")
    void deveTestarToString() {
        // Dado
        Usuario usuario = new Usuario(nomeValido, emailValido, matriculaValida, senhaValida);
        
        // Quando
        String stringRepresentation = usuario.toString();
        
        // Então
        assertNotNull(stringRepresentation);
        // Opcional: Verifica se o toString contém dados relevantes
        assertTrue(stringRepresentation.contains(nomeValido)); 
    }
}
