package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    // Relacionamento: Um Curso tem muitos Conteúdos
    // cascade = ALL: Se eu salvar um curso, salva os conteúdos. Se eu deletar, deleta os conteúdos.
    // orphanRemoval = true: Se eu remover um conteúdo da lista, ele é deletado do banco.
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Conteudo> conteudos = new ArrayList<>();

    // Relacionamento: Muitos Cursos podem ter muitos Usuários (alunos)
    @ManyToMany(mappedBy = "cursosInscritos", fetch = FetchType.LAZY)
    private Set<Usuario> alunosInscritos = new HashSet<>();

    // Construtor padrão (exigido pelo JPA)
    public Curso() {
    }

    // Construtor de entidade
    public Curso(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public List<Conteudo> getConteudos() { return conteudos; }
    public Set<Usuario> getAlunosInscritos() { return alunosInscritos; }

    // --- Métodos Helper (Boas Práticas) ---
    
    /**
     * Adiciona um conteúdo a este curso, mantendo a consistência
     * do relacionamento bidirecional.
     */
    public void addConteudo(Conteudo conteudo) {
        this.conteudos.add(conteudo);
        conteudo.setCurso(this); // Seta a referência "pai" no "filho"
    }

    /**
     * Remove um conteúdo deste curso.
     */
    public void removeConteudo(Conteudo conteudo) {
        this.conteudos.remove(conteudo);
        conteudo.setCurso(null);
    }
}