package curso.modelo;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private String nome;
    private List<String> aulas;

    // implementação para o teste 1 passar - Pedro
    public Curso(String nome) {
    	
    	if (nome == null || nome.trim().isEmpty())
    	{
    		throw new IllegalArgumentException("O nome do curso não pode ser nulo ou vazio.");
    	}
        this.nome = nome;        
        this.aulas = new ArrayList<>();
    }
    public String getNome() {
        return this.nome;
    }
    
 // implementação para o teste 2 passar - André
    public void adicionaAula(String aula) {
    	if (aula == null || aula.trim().isEmpty())
    	{
    		throw new IllegalArgumentException("O nome da aula não pode ser nulo ou vazio.");
    	}
        this.aulas.add(aula);
    }

    public String[] getAulas() {
        return this.aulas.toArray(new String[0]);
    }
}