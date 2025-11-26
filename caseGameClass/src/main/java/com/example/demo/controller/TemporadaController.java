package com.example.demo.controller;

import com.example.demo.dto.TemporadaRequestDTO;
import com.example.demo.dto.TemporadaResponseDTO;
import com.example.demo.entity.Temporada;
import com.example.demo.service.TemporadaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temporadas")
@CrossOrigin
public class TemporadaController {

    private final TemporadaService temporadaService;

    public TemporadaController(TemporadaService temporadaService) {
        this.temporadaService = temporadaService;
    }

    /**
     * Cria uma nova temporada competitiva.
     * Endpoint: POST /api/temporadas
     */
    @PostMapping
    public ResponseEntity<TemporadaResponseDTO> criarTemporada(@Valid @RequestBody TemporadaRequestDTO dto) {
        TemporadaResponseDTO response = temporadaService.criarTemporada(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retorna qual é a temporada que está valendo AGORA.
     * Endpoint: GET /api/temporadas/ativa
     */
    @GetMapping("/ativa")
    public ResponseEntity<TemporadaResponseDTO> buscarTemporadaAtiva() {
        // O service retorna a Entidade, aqui é feita uma conversão rápida para DTO
        Temporada temporada = temporadaService.buscarTemporadaAtiva();
        return ResponseEntity.ok(new TemporadaResponseDTO(temporada));
    }

    /**
     * Lista todas as temporadas (histórico).
     * Endpoint: GET /api/temporadas
     */
    @GetMapping
    public ResponseEntity<List<TemporadaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(temporadaService.listarTodas());
    }
}