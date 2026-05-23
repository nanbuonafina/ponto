package com.pontoeletronico.backend.controller;

import com.pontoeletronico.backend.model.RegistroPonto;
import com.pontoeletronico.backend.model.Usuario;

import com.pontoeletronico.backend.service.RegistroPontoService;
import com.pontoeletronico.backend.service.UsuarioService;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ponto")
public class PontoController {

    private final RegistroPontoService pontoService;

    private final UsuarioService usuarioService;

    public PontoController(
            RegistroPontoService pontoService,
            UsuarioService usuarioService
    ) {

        this.pontoService = pontoService;
        this.usuarioService = usuarioService;
    }


    @PostMapping("/registrar")
    public RegistroPonto registrar(
            Authentication authentication
    ) {

        System.out.println("AUTH: " + authentication);

        String email =
                authentication.getName();

        System.out.println("EMAIL: " + email);

        Usuario usuario =
                usuarioService
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Usuário não encontrado"
                                )
                        );

        return pontoService.registrar(usuario);
    }

    @GetMapping("/meus")
    public List<RegistroPonto> meusPontos(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        Usuario usuario =
                usuarioService
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Usuário não encontrado"
                                )
                        );

        return pontoService.listar(usuario);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/todos")
    public List<RegistroPonto> todos() {

        return pontoService.listarTodos();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/funcionario/{id}")
    public List<RegistroPonto> porFuncionario(
            @PathVariable Long id
    ) {

        return pontoService.listarPorUsuario(id);
    }
}