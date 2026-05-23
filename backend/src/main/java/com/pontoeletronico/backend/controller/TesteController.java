package com.pontoeletronico.backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping("/teste")
    public String livre() {

        return "Livre";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {

        return "Área admin";
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN', 'FUNCIONARIO')"
    )
    @GetMapping("/usuario")
    public String usuario() {

        return "Área usuário";
    }
}