package com.pontoeletronico.backend.repository;

import com.pontoeletronico.backend.model.RegistroPonto;
import com.pontoeletronico.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Long> {
    List<RegistroPonto> findByUsuario(Usuario usuario);

    Optional<RegistroPonto> findTopByUsuarioOrderByDataHoraDesc(Usuario usuario);
}