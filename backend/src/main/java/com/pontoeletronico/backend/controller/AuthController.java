package com.pontoeletronico.backend.controller;

import com.pontoeletronico.backend.dto.LoginRequestDTO;
import com.pontoeletronico.backend.dto.LoginResponseDTO;
import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UsuarioService service;

    public AuthController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        return service.salvar(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDTO dto
    ) {

        boolean autenticado =
                service.autenticar(
                        dto.getEmail(),
                        dto.getSenha()
                );

        if (!autenticado) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha inválidos");
        }

        Usuario usuario = service
                .findByEmail(dto.getEmail())
                .get();

        LoginResponseDTO response =
                new LoginResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getRole()
                );

        return ResponseEntity.ok(response);
    }
}