package com.pontoeletronico.backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AtualizarUsuarioDTO {

    private String nome;

    private String genero;

    private LocalDate dataNascimento;
}