package com.pontoeletronico.backend.service;

import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

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

    public Usuario salvar(Usuario usuario) {

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        return repository.save(usuario);
    }

    public boolean autenticar(String email, String senha) {

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