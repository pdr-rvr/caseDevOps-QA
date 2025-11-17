package com.example.demo.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_user")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Embedded
    @AttributeOverride(name = "enderecoEmail", column = @Column(name = "email", unique = true, nullable = false))
    private Email email;

    @Embedded
    @AttributeOverride(name = "ra", column = @Column(name = "matricula", unique = true, nullable = false))
    private Matricula matricula;

    @Embedded
    @AttributeOverride(name = "hash", column = @Column(name = "senha", nullable = false))
    private Senha senha;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_usuario_curso",
               joinColumns = @JoinColumn(name = "usuario_id"),
               inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private Set<Curso> cursosInscritos = new HashSet<>();

    protected Usuario() {
    }

    // --- CONSTRUTOR ---
    public Usuario(String nome, Email email, Matricula matricula, Senha senha) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.senha = senha;
    }

    // --- GETTERS ---

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Email getEmail() {
        return email;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public Senha getSenha() {
        return senha;
    }

    public Set<Curso> getCursosInscritos() {
        return cursosInscritos;
    }

    // --- Métodos de Domínio ---

    public void alterarNome(String novoNome) {
        if (novoNome == null || novoNome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        this.nome = novoNome;
    }

    public void alterarSenha(Senha novaSenha) {
        Objects.requireNonNull(novaSenha, "Nova senha não pode ser nula");
        this.senha = novaSenha;
    }

    public void inscreverEmCurso(Curso curso) {
        this.cursosInscritos.add(curso);
        curso.getAlunosInscritos().add(this);
    }

    public void cancelarInscricao(Curso curso) {
        this.cursosInscritos.remove(curso);
        curso.getAlunosInscritos().remove(this);
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    // --- Equals e HashCode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(matricula, usuario.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email=" + email.getEnderecoEmail() +
                ", matricula=" + matricula.getRa() +
                ", senha=***" +
                '}';
    }
}