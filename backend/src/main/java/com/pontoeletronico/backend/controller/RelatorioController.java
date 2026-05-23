package com.pontoeletronico.backend.controller;

import com.pontoeletronico.backend.model.RegistroPonto;
import com.pontoeletronico.backend.model.Usuario;

import com.pontoeletronico.backend.service.ExcelService;
import com.pontoeletronico.backend.service.RegistroPontoService;
import com.pontoeletronico.backend.service.UsuarioService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final ExcelService excelService;

    private final RegistroPontoService pontoService;

    private final UsuarioService usuarioService;

    public RelatorioController(
            ExcelService excelService,
            RegistroPontoService pontoService,
            UsuarioService usuarioService
    ) {

        this.excelService = excelService;
        this.pontoService = pontoService;
        this.usuarioService = usuarioService;
    }


    @GetMapping("/meus")
    public ResponseEntity<byte[]> exportarMeus(
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

        List<RegistroPonto> registros =
                pontoService.listar(usuario);

        byte[] excel =
                excelService.gerarExcel(registros);

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=meus_pontos.xlsx"
                )

                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )

                .body(excel);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/todos")
    public ResponseEntity<byte[]> exportarTodos() {

        List<RegistroPonto> registros =
                pontoService.listarTodos();

        byte[] excel =
                excelService.gerarExcel(registros);

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=todos_pontos.xlsx"
                )

                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )

                .body(excel);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/funcionario/{id}")
    public ResponseEntity<byte[]> exportarFuncionario(
            @PathVariable Long id
    ) {

        List<RegistroPonto> registros =
                pontoService.listarPorUsuario(id);

        byte[] excel =
                excelService.gerarExcel(registros);

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=funcionario.xlsx"
                )

                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )

                .body(excel);
    }
}