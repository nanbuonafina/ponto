package com.pontoeletronico.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senhaHash;

    private LocalDateTime dataAlteracao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}