package com.pontoeletronico.backend.dto;

import lombok.Data;

@Data
public class AlterarSenhaDTO {

    private String email;

    private String senhaAtual;

    private String novaSenha;
}