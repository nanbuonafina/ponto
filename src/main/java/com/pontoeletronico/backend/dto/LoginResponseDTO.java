package com.pontoeletronico.backend.dto;

import com.pontoeletronico.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private Role role;
}