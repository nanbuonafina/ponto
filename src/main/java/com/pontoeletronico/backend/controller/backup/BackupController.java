package com.pontoeletronico.backend.controller.backup;

import com.pontoeletronico.backend.service.backup.BackupService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/backup")
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }
    
    @PostMapping("/gerar")
    public String gerarBackup() {
        return backupService.realizarBackup();
    }

    @PostMapping("/restaurar")
    public String restaurarBackup(@RequestParam String arquivo) {
        return backupService.restaurarBackup(arquivo);
    }

    @GetMapping("/listar")
    public List<String> listarBackups() {
        return backupService.listarBackups();
    }
}