package com.pontoeletronico.backend.controller;

import com.pontoeletronico.backend.dto.AtualizarUsuarioDTO;
import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.service.UsuarioService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(
            UsuarioService usuarioService
    ) {

        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public Usuario me(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return usuarioService
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );
    }

    @PutMapping("/me")
    public Usuario atualizarPerfil(
            Authentication authentication,
            @RequestBody AtualizarUsuarioDTO dto
    ) {

        String email =
                authentication.getName();

        return usuarioService.atualizarPerfil(
                email,
                dto
        );
    }
}