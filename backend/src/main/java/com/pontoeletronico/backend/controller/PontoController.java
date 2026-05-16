package com.pontoeletronico.backend.controller;

import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.service.RegistroPontoService;
import com.pontoeletronico.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ponto")
public class PontoController {

    private final RegistroPontoService pontoService;
    private final UsuarioService usuarioService;

    public PontoController(RegistroPontoService pontoService, UsuarioService usuarioService) {
        this.pontoService = pontoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public Object registrar(@RequestParam String email) {
        Usuario usuario = usuarioService.findByEmail(email).orElseThrow();
        return pontoService.registrar(usuario);
    }

    @GetMapping("/historico")
    public List<?> historico(@RequestParam String email) {
        Usuario usuario = usuarioService.findByEmail(email).orElseThrow();
        return pontoService.listar(usuario);
    }
}