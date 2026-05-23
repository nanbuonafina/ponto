package com.pontoeletronico.backend.service;

import com.pontoeletronico.backend.model.RegistroPonto;
import com.pontoeletronico.backend.model.TipoRegistro;
import com.pontoeletronico.backend.model.Usuario;

import com.pontoeletronico.backend.repository.RegistroPontoRepository;
import com.pontoeletronico.backend.repository.UsuarioRepository;
import com.pontoeletronico.backend.service.UsuarioService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroPontoService {

    private final RegistroPontoRepository repository;

    private final UsuarioRepository usuarioRepository;

    private final UsuarioService usuarioService;

    public RegistroPontoService(
            RegistroPontoRepository repository,
            UsuarioRepository usuarioRepository,
            UsuarioService usuarioService
    ) {

        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }


    public RegistroPonto registrar(
            Usuario usuario
    ) {

        RegistroPonto registro =
                new RegistroPonto();

        registro.setUsuario(usuario);

        registro.setDataHora(
                LocalDateTime.now()
        );

        TipoRegistro tipo = repository

                .findTopByUsuarioOrderByDataHoraDesc(
                        usuario
                )

                .map(r ->

                        r.getTipo() == TipoRegistro.ENTRADA

                                ? TipoRegistro.SAIDA

                                : TipoRegistro.ENTRADA
                )

                .orElse(
                        TipoRegistro.ENTRADA
                );

        registro.setTipo(tipo);

        return repository.save(registro);
    }


    public List<RegistroPonto> listar(
            Usuario usuario
    ) {

        return repository.findByUsuario(usuario);
    }


    public List<RegistroPonto> listarTodos() {

        return repository.findAll();
    }

    
    public List<RegistroPonto> listarPorUsuario(
            Long usuarioId
    ) {

        Usuario usuario = usuarioRepository

                .findById(usuarioId)

                .orElseThrow(
                        () -> new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        return repository.findByUsuario(usuario);
    }
}