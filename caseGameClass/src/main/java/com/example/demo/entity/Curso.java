package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_curso")
@Getter // Gera todos os Getters (getId, getNome, getConteudos, etc.)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Substitui o construtor public Curso() {}
@EqualsAndHashCode(of = "nome") // Define a igualdade baseada na chave de negócio (nome único)
@ToString(exclude = {"conteudos", "alunosInscritos"}) // Evita loops infinitos e LazyInitializationException
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

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Conteudo> conteudos = new ArrayList<>();

    @ManyToMany(mappedBy = "cursosInscritos", fetch = FetchType.LAZY)
    private Set<Usuario> alunosInscritos = new HashSet<>();

    public Curso(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }


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