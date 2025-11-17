package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "tb_conteudo")
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String nome; // Ex: "Aula 1: Introdução"

    @NotBlank
    @URL // Valida se é uma URL válida
    @Column(nullable = false)
    private String urlVideo;

    // Relacionamento: Muitos Conteúdos pertencem a Um Curso
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    
    // Construtor padrão
    public Conteudo() {
    }

    // Construtor de entidade
    public Conteudo(String nome, String urlVideo) {
        this.nome = nome;
        this.urlVideo = urlVideo;
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getUrlVideo() { return urlVideo; }
    public Curso getCurso() { return curso; }

    // Setter especial para o relacionamento bidirecional
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}