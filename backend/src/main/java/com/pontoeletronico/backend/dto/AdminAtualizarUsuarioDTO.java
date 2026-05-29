package com.pontoeletronico.backend.dto;

import com.pontoeletronico.backend.model.Role;
import lombok.Data;

@Data
public class AdminAtualizarUsuarioDTO {

    private String nome;

    private String email;

    private Role role;
}