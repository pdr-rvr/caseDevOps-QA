package dto;

import entity.Usuario;
import java.util.Objects;

/**
 * DTO para exibir dados do usuário ao cliente.
 */
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String matricula;

    /**
     * Construtor padrão para serialização
     */
    public UsuarioResponseDTO() {
    }

    /**
     * Construtor de conveniência para mapear
     *
     * @param entity A entidade Usuario vinda do banco.
     */
    public UsuarioResponseDTO(Usuario entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.email = entity.getEmail().getEnderecoEmail();
        this.matricula = entity.getMatricula().getRa();
    }

    // --- Getters e Setters ---
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioResponseDTO that = (UsuarioResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(matricula, that.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matricula);
    }
}