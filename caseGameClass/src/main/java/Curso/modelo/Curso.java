package Curso.modelo;

// Importações necessárias (neste caso, para List e ArrayList)
import java.util.ArrayList;
import java.util.List;

/**
 * Esta é a implementação mínima da classe Curso, criada apenas
 * para fazer a classe de teste CursoTest compilar e rodar.
 * (Fase "Red" do TDD)
 */
public class Curso {

    // Adicione os atributos mínimos que você precisará
    private String nome;
    private List<String> aulas; // Usar uma Lista é geralmente mais flexível que um array

    /**
     * Construtor necessário pelo teste: new Curso("Introducao a Java");
     */
    public Curso(String nome) {
        // Por enquanto, não faremos nada, ou podemos inicializar as variáveis.
        this.nome = null; // Forçando o primeiro teste a falhar
        this.aulas = new ArrayList<>();
    }

    /**
     * Método getter necessário pelo teste: curso.getNome();
     */
    public String getNome() {
        // Retorna um valor que fará o teste falhar na asserção.
        return this.nome;
    }

    /**
     * Método necessário pelo teste: curso.adicionaAula("...");
     */
    public void adicionaAula(String aula) {
        // Nenhuma lógica por enquanto. O método só precisa existir.
    }

    /**
     * Método necessário pelo teste: curso.getAulas();
     */
    public String[] getAulas() {
        // Retorna um valor que fará o teste falhar na asserção.
        // Um array vazio é uma boa opção para começar.
        return new String[0];
    }
}