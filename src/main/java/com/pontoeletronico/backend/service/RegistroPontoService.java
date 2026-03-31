package com.pontoeletronico.backend.service;

import com.pontoeletronico.backend.model.*;
import com.pontoeletronico.backend.repository.RegistroPontoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroPontoService {

    private final RegistroPontoRepository repository;

    public RegistroPontoService(RegistroPontoRepository repository) {
        this.repository = repository;
    }

    public RegistroPonto registrar(Usuario usuario) {

        RegistroPonto registro = new RegistroPonto();
        registro.setUsuario(usuario);
        registro.setDataHora(LocalDateTime.now());

        TipoRegistro tipo = repository
            .findTopByUsuarioOrderByDataHoraDesc(usuario)
            .map(r -> r.getTipo() == TipoRegistro.ENTRADA ? TipoRegistro.SAIDA : TipoRegistro.ENTRADA)
            .orElse(TipoRegistro.ENTRADA);

        registro.setTipo(tipo);

        return repository.save(registro);
    }

    public List<RegistroPonto> listar(Usuario usuario) {
        return repository.findByUsuario(usuario);
    }
}