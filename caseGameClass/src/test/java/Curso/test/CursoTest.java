package curso.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import curso.modelo.Curso;

@DisplayName("Testes da entidade Curso")
public class CursoTest {

	@Test
	@DisplayName("Deve criar um curso com o nome correto") // Nome mais descritivo para o teste
	public void deveCriarCursoComNomeCorreto() {
		// Arrange (Organização)
		String nomeEsperado = "Introducao a Java";
		
		// Act (Ação)
		Curso curso = new Curso(nomeEsperado);
		
		// Assert (Verificação)
		assertNotNull(curso, "O objeto curso nao deveria ser nulo.");
		assertEquals(nomeEsperado, curso.getNome(), "O nome do curso nao corresponde ao esperado.");
	}
	
	@Test
	@DisplayName("Deve adicionar multiplas aulas a um curso e mante-las na ordem de insercao")
	public void deveAdicionarMultiplasAulasAoCurso() {
		// Arrange
		Curso curso = new Curso("Introducao a Java");
		String[] aulasEsperadas = {"Tipos primitivos", "Variaveis", "Estruturas de decisao", "Estruturas de repeticao"};
		
		// Act
		curso.adicionaAula("Tipos primitivos");
		curso.adicionaAula("Variaveis");
		curso.adicionaAula("Estruturas de decisao");
		curso.adicionaAula("Estruturas de repeticao");
		
		String[] aulasCadastradas = curso.getAulas();
		
		// Assert
		// PONTO CRÍTICO: Usar assertArrayEquals para comparar o CONTEÚDO de arrays.
		// assertEquals compararia apenas a referência de memória, o que sempre falharia.
		assertArrayEquals(aulasEsperadas, aulasCadastradas, "A lista de aulas cadastradas nao corresponde a esperada.");
	}
	
	@Test
	@DisplayName("Não deve permitir criar curso com nome vazio")
	public void naoDevePermitirCursoComNomeVazio() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	        new Curso("");
	    });
	    assertEquals("O nome do curso não pode ser nulo ou vazio.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Não deve permitir criar curso com nome nulo")
	public void naoDevePermitirCursoComNomeNulo() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Curso(null);
	    });
	}
	
	@Test
	@DisplayName("Não deve permitir criar aula com nome vazio")
	public void naoDevePermitirAulaComNomeVazio() {
		Curso curso = new Curso("Introducao a Java");
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	        curso.adicionaAula("");
	    });
	    assertEquals("O nome da aula não pode ser nulo ou vazio.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Não deve permitir criar aula com nome nulo")
	public void naoDevePermitirAulaComNomeNulo() {
		Curso curso = new Curso("Introducao a Java");
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	        curso.adicionaAula(null);
	    });
	    assertEquals("O nome da aula não pode ser nulo ou vazio.", exception.getMessage());
	}
}