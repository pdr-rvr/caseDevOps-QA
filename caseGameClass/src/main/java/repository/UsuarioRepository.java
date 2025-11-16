package repository;

import entity.Email;
import entity.Matricula;
import entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Procura um usuário pela sua matrícula (Value Object).
     *
     * @param matricula O VO Matricula.
     * @return Um Optional contendo o Usuário, se encontrado.
     */
    Optional<Usuario> findByMatricula(Matricula matricula);

    /**
     * Procura um usuário pelo seu email (Value Object).
     *
     * @param email O VO Email.
     * @return Um Optional contendo o Usuário, se encontrado.
     */
    Optional<Usuario> findByEmail(Email email);

    /**
     * Verifica eficientemente se já existe um usuário com esta matrícula.
     *
     * @param matricula O VO Matricula.
     * @return true se a matrícula já existir no banco, false caso contrário.
     */
    boolean existsByMatricula(Matricula matricula);

    /**
     * Verifica eficientemente se já existe um usuário com este email.
     *
     * @param email O VO Email.
     * @return true se o email já existir no banco, false caso contrário.
     */
    boolean existsByEmail(Email email);
}
