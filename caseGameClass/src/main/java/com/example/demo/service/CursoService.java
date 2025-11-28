package com.example.demo.service;

import com.example.demo.dto.CursoRequestDTO;
import com.example.demo.dto.CursoResponseDTO;
import com.example.demo.entity.Conteudo;
import com.example.demo.entity.Curso;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.example.demo.config.RabbitMQConfig;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Método para buscar todos os cursos
    public List<CursoResponseDTO> listarTodosCursos() {
        return cursoRepository.findAll().stream()
                .map(CursoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Método para buscar um curso por ID
    public CursoResponseDTO buscarCursoPorId(Long id) {
        Curso curso = cursoRepository.findByIdWithConteudos(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        return new CursoResponseDTO(curso);
    }

    // Método para criar um novo curso
    @Transactional
    public CursoResponseDTO criarCurso(CursoRequestDTO requestDTO) {
        // 1. Validação de negócio
        if (cursoRepository.existsByNome(requestDTO.getNome())) {
            throw new IllegalArgumentException("Um curso com este nome já existe");
        }

        // 2. Mapear DTO para Entidade
        Curso novoCurso = new Curso(requestDTO.getNome(), requestDTO.getDescricao());

        // 3. Mapear e adicionar os conteúdos
        requestDTO.getConteudos().forEach(conteudoDTO -> {
            Conteudo novoConteudo = new Conteudo(conteudoDTO.getNome(), conteudoDTO.getUrlVideo());
            novoCurso.addConteudo(novoConteudo); // O helper "addConteudo" cuida da relação
        });

        // 4. Salvar (O CascadeType.ALL salva os conteúdos juntos)
        Curso cursoSalvo = cursoRepository.save(novoCurso);
        
        // 5. Retornar o DTO de resposta
        return new CursoResponseDTO(cursoSalvo);
    }

    // Método para inscrever um usuário em um curso
    @Transactional
    public void inscreverUsuarioEmCurso(Long usuarioId, Long cursoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        // Usamos o método helper da entidade
        usuario.inscreverEmCurso(curso);
        
        // Salvamos o "dono" do relacionamento (Usuario)
        usuarioRepository.save(usuario);

        String mensagem = "Nova inscrição: Usuário " + usuario.getNome() + " no curso " + curso.getNome();
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_INSCRICOES, mensagem);
    }

    // Método para cancelar inscrição
    @Transactional
    public void cancelarInscricao(Long usuarioId, Long cursoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        // Usamos o método helper
        usuario.cancelarInscricao(curso);
        
        usuarioRepository.save(usuario);
    }
}
