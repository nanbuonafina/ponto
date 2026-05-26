package com.pontoeletronico.backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Integer tentativasFalhas = 0;

    private LocalDateTime bloqueadoAte;

    private String genero;

    private LocalDate dataNascimento;
}
