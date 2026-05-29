package com.pontoeletronico.backend.controller;

import com.pontoeletronico.backend.dto.AdminAtualizarUsuarioDTO;
import com.pontoeletronico.backend.model.RegistroPonto;
import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.service.RegistroPontoService;
import com.pontoeletronico.backend.service.UsuarioService;
import com.pontoeletronico.backend.service.backup.BackupService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")

@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UsuarioService usuarioService;

    private final RegistroPontoService pontoService;

    private final BackupService backupService;

    public AdminController(
            UsuarioService usuarioService,
            RegistroPontoService pontoService,
            BackupService backupService
    ) {

        this.usuarioService = usuarioService;
        this.pontoService = pontoService;
        this.backupService = backupService;
    }

    // =========================
    // USUÁRIOS
    // =========================

    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {

        return usuarioService.listarUsuarios();
    }

    @DeleteMapping("/usuarios/{id}")
    public String excluirUsuario(
            @PathVariable Long id
    ) {

        usuarioService.excluirUsuario(id);

        return "Usuário excluído com sucesso";
    }

    @PutMapping("/usuarios/{id}")
    public Usuario atualizarUsuario(
            @PathVariable Long id,
            @RequestBody AdminAtualizarUsuarioDTO dto
    ) {

        return usuarioService.adminAtualizarUsuario(
                id,
                dto
        );
    }

    // =========================
    // PONTOS
    // =========================

    @GetMapping("/pontos")
    public List<RegistroPonto> listarPontos() {

        return pontoService.listarTodos();
    }

    @GetMapping("/pontos/usuario/{id}")
    public List<RegistroPonto> listarPontosUsuario(
            @PathVariable Long id
    ) {

        return pontoService.listarPorUsuario(id);
    }

    // =========================
    // BACKUPS
    // =========================

    @GetMapping("/backups")
    public List<String> listarBackups() {

        return backupService.listarBackups();
    }

    @PostMapping("/backup")
    public String gerarBackup() {

        return backupService.realizarBackup();
    }

    @PostMapping("/restore")
    public String restaurarBackup(
            @RequestParam String arquivo
    ) {

        return backupService.restaurarBackup(arquivo);
    }
}