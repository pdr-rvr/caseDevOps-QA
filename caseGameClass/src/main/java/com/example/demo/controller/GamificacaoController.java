package com.example.demo.controller;

import com.example.demo.dto.ItemPlacarDTO;
import com.example.demo.dto.ResultadoResponseDTO;
import com.example.demo.dto.SolucaoAvaliacaoDTO;
import com.example.demo.service.GamificacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gamificacao")
@CrossOrigin
public class GamificacaoController {

    private final GamificacaoService gamificacaoService;

    public GamificacaoController(GamificacaoService gamificacaoService) {
        this.gamificacaoService = gamificacaoService;
    }

    /**
     * AÇÃO PRINCIPAL: Aluno envia a solução de uma avaliação.
     * O sistema corrige, salva o resultado e atualiza o ranking.
     * * Endpoint: POST /api/gamificacao/avaliacoes/{avaliacaoId}/resolver
     */
    @PostMapping("/avaliacoes/{avaliacaoId}/resolver")
    public ResponseEntity<ResultadoResponseDTO> resolverAvaliacao(
            @PathVariable Long avaliacaoId,
            @Valid @RequestBody SolucaoAvaliacaoDTO solucaoDTO) {
        
        ResultadoResponseDTO resultado = gamificacaoService.registrarSolucaoAvaliacao(avaliacaoId, solucaoDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    /**
     * Consulta o Placar (Leaderboard) da temporada atual.
     * * Endpoint: GET /api/gamificacao/placar
     */
    @GetMapping("/placar")
    public ResponseEntity<List<ItemPlacarDTO>> verPlacarAtual() {
        List<ItemPlacarDTO> ranking = gamificacaoService.gerarPlacarAtual();
        
        // Retorna 200 OK com a lista (pode ser vazia se ninguém jogou ainda)
        return ResponseEntity.ok(ranking);
    }
}