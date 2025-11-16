package controller;

import dto.UsuarioRequestDTO;
import dto.UsuarioResponseDTO;
import jakarta.validation.Valid; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UsuarioService;

import java.util.List;

@RestController 
@CrossOrigin
@RequestMapping("/api/usuarios") 
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Injeção de dependência via construtor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para criar um novo usuário.
     * Mapeia para: POST /api/usuarios
     *
     * @param requestDTO O corpo da requisição contendo os dados do usuário.
     * @Valid aciona as validações (@NotBlank, @Email, etc.) do DTO.
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarNovoUsuario(@Valid @RequestBody UsuarioRequestDTO requestDTO) {
        UsuarioResponseDTO responseDTO = usuarioService.criarNovoUsuario(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Endpoint para listar todos os usuários.
     * Mapeia para: GET /api/usuarios
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodosUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.getAllUsers();

        return ResponseEntity.ok(usuarios);
    }
}