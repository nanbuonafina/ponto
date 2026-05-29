package com.pontoeletronico.backend.dto;

import com.pontoeletronico.backend.model.LogSistema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashboardAdminDTO {

    private long totalUsuarios;

    private long totalAdministradores;

    private long totalFuncionarios;

    private long usuariosBloqueados;

    private long totalRegistrosPonto;

    private long registrosHoje;

    private long totalBackups;

    private long totalLogs;

    private long loginsHoje;

    private long falhasLoginHoje;

    private List<LogSistema> ultimosLogs;
}