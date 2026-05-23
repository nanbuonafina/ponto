package com.pontoeletronico.backend.service;

import com.pontoeletronico.backend.dto.RegisterRequestDTO;
import com.pontoeletronico.backend.model.Role;
import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.codigo}")
    private String adminCode;

    public UsuarioService(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> findByEmail(String email) {

        return repository.findByEmail(email);
    }

    public Usuario salvar(RegisterRequestDTO dto) {
        
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (dto.getRole() == Role.ADMIN) {

            if (
                    dto.getAdminCode() == null ||
                    !dto.getAdminCode().equals(adminCode)
            ) {

                throw new RuntimeException(
                        "Código de administrador inválido"
                );
            }
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        usuario.setSenha(
                passwordEncoder.encode(dto.getSenha())
        );

        usuario.setRole(dto.getRole());

        return repository.save(usuario);
    }

    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    public boolean autenticar(
            String email,
            String senha
    ) {

        Optional<Usuario> usuarioOpt =
                repository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioOpt.get();

        return passwordEncoder.matches(
                senha,
                usuario.getSenha()
        );
    }
}