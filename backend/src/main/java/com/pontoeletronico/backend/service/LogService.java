package com.pontoeletronico.backend.service;

import com.pontoeletronico.backend.model.LogTipo;

import org.springframework.stereotype.Service;

import com.pontoeletronico.backend.model.LogSistema;
import com.pontoeletronico.backend.repository.LogSistemaRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class LogService {

    private static final String DIRETORIO = "logs/";
    private static final String ARQUIVO = "logs/sistema.log";
    private final LogSistemaRepository logRepository;

    public LogService(
        LogSistemaRepository logRepository
    ) {
        this.logRepository = logRepository;
    }

    public void registrar(
            LogTipo tipo,
            String usuario,
            String descricao
    ) {

        try {

            File pasta = new File(DIRETORIO);

            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            FileWriter fw =
                    new FileWriter(ARQUIVO, true);

            PrintWriter pw =
                    new PrintWriter(fw);

            String dataHora =
                    LocalDateTime.now()
                            .format(
                                    DateTimeFormatter.ofPattern(
                                            "dd/MM/yyyy HH:mm:ss"
                                    )
                            );

            String linha =
                    String.format(
                            "[%s] [%s] [USUARIO: %s] %s",
                            dataHora,
                            tipo.name(),
                            usuario,
                            descricao
                    );
            LogSistema log = new LogSistema();

            log.setDataHora(LocalDateTime.now());
            log.setTipo(tipo);
            log.setUsuario(usuario);
            log.setDescricao(descricao);
            logRepository.save(log);

            pw.println(linha);

            pw.close();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao registrar log",
                    e
            );
        }
    }

    public List<LogSistema> listarTodos() {
        return logRepository.findAll();
    }

    public List<LogSistema> listarRecentes() {
        return logRepository.findTop20ByOrderByDataHoraDesc();
    } 

    public List<LogSistema> buscarPorTipo(
                LogTipo tipo
    ) {
        return logRepository.findByTipo(tipo);
    }

    public List<LogSistema> buscarPorUsuario(
                String usuario
    ) {
        return logRepository
                .findByUsuarioContainingIgnoreCase(
                        usuario
                );
    }

    public List<LogSistema> buscarPorPeriodo(
                LocalDateTime inicio,
                LocalDateTime fim
    ) {
        return logRepository
                .findByDataHoraBetween(
                        inicio,
                        fim
                );
    }

    public List<LogSistema> filtrarLogs(
                LogTipo tipo,
                String usuario,
                LocalDateTime inicio,
                LocalDateTime fim
    ) {

        boolean temTipo = tipo != null;
        boolean temUsuario = usuario != null && !usuario.isBlank();
        boolean temPeriodo = inicio != null && fim != null;

        if (temTipo && temUsuario && temPeriodo) {
                return logRepository
                        .findByTipoAndUsuarioContainingIgnoreCaseAndDataHoraBetween(
                                tipo,
                                usuario,
                                inicio,
                                fim
                        );
        }

        if (temTipo && temUsuario) {
                return logRepository
                        .findByTipoAndUsuarioContainingIgnoreCase(
                                tipo,
                                usuario
                        );
        }

        if (temTipo && temPeriodo) {
                return logRepository
                        .findByTipoAndDataHoraBetween(
                                tipo,
                                inicio,
                                fim
                        );
        }

        if (temUsuario && temPeriodo) {
                return logRepository
                        .findByUsuarioContainingIgnoreCaseAndDataHoraBetween(
                                usuario,
                                inicio,
                                fim
                        );
        }

        if (temTipo) {
                return logRepository.findByTipo(tipo);
        }

        if (temUsuario) {
                return logRepository
                        .findByUsuarioContainingIgnoreCase(
                                usuario
                        );
        }

        if (temPeriodo) {
                return logRepository
                        .findByDataHoraBetween(
                                inicio,
                                fim
                        );
        }

        return logRepository.findAll();
        }
}