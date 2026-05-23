package com.pontoeletronico.backend.dto;

import com.pontoeletronico.backend.model.Role;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private Role role;

    private String adminCode;
}