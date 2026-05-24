package com.pontoeletronico.backend.controller;

import com.pontoeletronico.backend.dto.LoginRequestDTO;
import com.pontoeletronico.backend.dto.LoginResponseDTO;
import com.pontoeletronico.backend.dto.RegisterRequestDTO;
import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.security.JwtService;
import com.pontoeletronico.backend.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
// @CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "*")

public class AuthController {

    private final UsuarioService service;
    private final JwtService jwtService;

    public AuthController(UsuarioService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
       @RequestBody RegisterRequestDTO dto
    ) {

        try {

                Usuario usuario = service.salvar(dto);

                return ResponseEntity.ok(usuario);

        } catch (RuntimeException e) {

                return ResponseEntity
                        .badRequest()
                        .body(e.getMessage());
        }
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

        String token = jwtService.gerarToken(usuario);

        LoginResponseDTO response =
                new LoginResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getRole(),
                        token
                );

        return ResponseEntity.ok(response);
    }
}