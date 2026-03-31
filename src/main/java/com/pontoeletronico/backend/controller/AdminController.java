package com.pontoeletronico.backend.controller;

import com.pontoeletronico.backend.repository.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final RegistroPontoRepository registroRepository;

    public AdminController(UsuarioRepository usuarioRepository,
                           RegistroPontoRepository registroRepository) {
        this.usuarioRepository = usuarioRepository;
        this.registroRepository = registroRepository;
    }

    @DeleteMapping("/limpar")
    public String limparBanco() {
        registroRepository.deleteAll();
        usuarioRepository.deleteAll();
        return "Banco limpo com sucesso!";
    }
}
