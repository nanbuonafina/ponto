package com.pontoeletronico.backend.repository;

import com.pontoeletronico.backend.model.HistoricoSenha;
import com.pontoeletronico.backend.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoSenhaRepository
        extends JpaRepository<HistoricoSenha, Long> {

    List<HistoricoSenha>
    findTop3ByUsuarioOrderByDataAlteracaoDesc(
            Usuario usuario
    );

    void deleteByUsuarioId(Long usuarioId);
}