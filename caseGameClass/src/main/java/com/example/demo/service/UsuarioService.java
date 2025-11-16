package com.example.demo.service;

import com.example.demo.dto.UsuarioRequestDTO;
import com.example.demo.dto.UsuarioResponseDTO;
import com.example.demo.entity.Email;
import com.example.demo.entity.Matricula;
import com.example.demo.entity.Senha;
import com.example.demo.entity.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import com.example.demo.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    // 1. Dependências injetadas via construtor e marcadas como 'final'
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // 2. Construtor para injeção de dependência
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Busca todos os usuários e os converte para DTO de resposta.
     */
    @Transactional(readOnly = true) 
    public List<UsuarioResponseDTO> getAllUsers() {
        List<Usuario> users = usuarioRepository.findAll();
        return users.stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional 
    public UsuarioResponseDTO criarNovoUsuario(UsuarioRequestDTO requestDTO) {

        // --- VALIDAÇÃO DE NEGÓCIO ---

        // 3. Cria os VOs (Value Objects) a partir do DTO
        Email email = new Email(requestDTO.getEmail());
        Matricula matricula = new Matricula(requestDTO.getMatricula());

        // 4. Validação de Regra de Negócio (Unicidade)
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("O email informado já está em uso.");
        }
        if (usuarioRepository.existsByMatricula(matricula)) {
            throw new IllegalArgumentException("A matrícula (RA) informada já está em uso.");
        }

        // --- LÓGICA DE NEGÓCIO ---

        // 4. Criptografar a senha (Hashing)
        String hashDaSenha = passwordEncoder.encode(requestDTO.getSenha());
        Senha senhaCriptografada = new Senha(hashDaSenha);

        // 5. Criar a entidade de domínio
        Usuario novoUsuario = new Usuario(
                requestDTO.getNome(),
                email,
                matricula,
                senhaCriptografada
        );

        // 6. Salvar a entidade no banco
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // 7. Retornar o DTO de Resposta
        return new UsuarioResponseDTO(usuarioSalvo);
    }
}