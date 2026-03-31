package com.pontoeletronico.backend.controller;

import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService service;

    public AuthController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        return service.salvar(usuario);
    }
}