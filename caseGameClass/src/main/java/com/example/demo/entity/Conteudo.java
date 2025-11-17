package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "tb_conteudo")
@Getter // Gera todos os Getters
@Setter // Gera todos os Setters
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Substitui o construtor public Conteudo() {}
@EqualsAndHashCode(of = {"nome", "urlVideo"}) // Para entidades, é seguro usar o ID para igualdade
@ToString(exclude = "curso") // Evita loop infinito no log
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
    
    public Conteudo(String nome, String urlVideo) {
        this.nome = nome;
        this.urlVideo = urlVideo;
    }

}