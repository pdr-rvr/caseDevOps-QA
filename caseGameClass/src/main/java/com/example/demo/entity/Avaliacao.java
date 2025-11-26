package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_avaliacao")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"titulo", "curso"}) 
@ToString(exclude = {"questoes", "curso"}) 
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    // Uma Avaliação pertence a um Curso
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    // Uma Avaliação tem várias Questões (Cascade ALL para salvar tudo junto)
    @OneToMany(mappedBy = "avaliacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Questao> questoes = new ArrayList<>();

    // --- CONSTRUTOR ---
    public Avaliacao(String titulo, Curso curso) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título da avaliação é obrigatório.");
        }
        this.titulo = titulo;
        this.curso = curso;
    }

    // --- MÉTODOS HELPER (Domínio Rico) ---
    
    public void adicionarQuestao(Questao questao) {
        this.questoes.add(questao);
        questao.setAvaliacao(this);
    }

    /**
     * Calcula a pontuação total máxima possível desta avaliação.
     */
    public Integer calcularPontuacaoMaxima() {
        return questoes.stream()
                .mapToInt(Questao::getPontos)
                .sum();
    }
}