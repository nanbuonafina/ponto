package com.pontoeletronico.backend.repository;

import com.pontoeletronico.backend.model.LogSistema;
import com.pontoeletronico.backend.model.LogTipo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogSistemaRepository
        extends JpaRepository<LogSistema, Long> {

    List<LogSistema> findTop20ByOrderByDataHoraDesc();

    long countByTipo(LogTipo tipo);

    long countByDataHoraAfter(
        LocalDateTime dataHora
    );

    long countByTipoAndDataHoraAfter(
            LogTipo tipo,
            LocalDateTime dataHora
    );

    List<LogSistema> findByTipo(
            LogTipo tipo
    );

    List<LogSistema> findByUsuarioContainingIgnoreCase(
            String usuario
    );

    List<LogSistema> findByDataHoraBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<LogSistema> findByTipoAndUsuarioContainingIgnoreCase(
            LogTipo tipo,
            String usuario
    );

    List<LogSistema> findByTipoAndDataHoraBetween(
            LogTipo tipo,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<LogSistema> findByUsuarioContainingIgnoreCaseAndDataHoraBetween(
            String usuario,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<LogSistema> findByTipoAndUsuarioContainingIgnoreCaseAndDataHoraBetween(
            LogTipo tipo,
            String usuario,
            LocalDateTime inicio,
            LocalDateTime fim
    );
}