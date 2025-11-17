package com.example.demo.controller;

import com.example.demo.dto.CursoRequestDTO;
import com.example.demo.dto.CursoResponseDTO;
import com.example.demo.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // Endpoint: Criar um novo curso
    @PostMapping
    public ResponseEntity<CursoResponseDTO> criarCurso(@Valid @RequestBody CursoRequestDTO requestDTO) {
        CursoResponseDTO responseDTO = cursoService.criarCurso(requestDTO);
        // Retorna 201 Created
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Endpoint: Listar todos os cursos
    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listarTodosCursos() {
        List<CursoResponseDTO> cursos = cursoService.listarTodosCursos();
        return ResponseEntity.ok(cursos); // Retorna 200 OK
    }

    // Endpoint: Buscar um curso pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> buscarCursoPorId(@PathVariable Long id) {
        CursoResponseDTO curso = cursoService.buscarCursoPorId(id);
        return ResponseEntity.ok(curso);
    }

    // Endpoint: Inscrever um usuário em um curso
    @PostMapping("/{cursoId}/inscrever/{usuarioId}")
    public ResponseEntity<Void> inscreverUsuario(@PathVariable Long cursoId, @PathVariable Long usuarioId) {
        cursoService.inscreverUsuarioEmCurso(usuarioId, cursoId);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    // Endpoint: Cancelar inscrição de um usuário
    @DeleteMapping("/{cursoId}/cancelar/{usuarioId}")
    public ResponseEntity<Void> cancelarInscricao(@PathVariable Long cursoId, @PathVariable Long usuarioId) {
        cursoService.cancelarInscricao(usuarioId, cursoId);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}