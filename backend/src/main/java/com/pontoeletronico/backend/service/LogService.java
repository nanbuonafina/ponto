package com.pontoeletronico.backend.service;

import com.pontoeletronico.backend.model.LogTipo;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogService {

    private static final String DIRETORIO = "logs/";
    private static final String ARQUIVO = "logs/sistema.log";

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

            pw.println(linha);

            pw.close();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao registrar log",
                    e
            );
        }
    }
}