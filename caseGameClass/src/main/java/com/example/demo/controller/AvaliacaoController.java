package com.example.demo.controller;

import com.example.demo.dto.AvaliacaoRequestDTO;
import com.example.demo.dto.AvaliacaoResponseDTO;
import com.example.demo.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin 
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    // Injeção de dependência via construtor
    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    /**
     * Cria uma nova avaliação (com questões e opções).
     * Endpoint: POST /api/avaliacoes
     */
    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(@Valid @RequestBody AvaliacaoRequestDTO requestDTO) {
        AvaliacaoResponseDTO responseDTO = avaliacaoService.criarAvaliacao(requestDTO);
        
        // Retorna status 201 (Created) com o corpo da resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Lista todas as avaliações de um curso específico.
     * Endpoint: GET /api/avaliacoes/curso/{cursoId}
     */
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorCurso(@PathVariable Long cursoId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.listarAvaliacoesPorCurso(cursoId);
        
        return ResponseEntity.ok(avaliacoes);
    }

    /**
     * Busca os detalhes completos de uma avaliação (incluindo questões).
     * Endpoint: GET /api/avaliacoes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> buscarPorId(@PathVariable Long id) {
        AvaliacaoResponseDTO avaliacao = avaliacaoService.buscarAvaliacaoPorId(id);
        
        return ResponseEntity.ok(avaliacao);
    }
}