package com.pontoeletronico.backend.service;

import com.pontoeletronico.backend.dto.RegisterRequestDTO;
import com.pontoeletronico.backend.model.HistoricoSenha;
import com.pontoeletronico.backend.model.Role;
import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.repository.HistoricoSenhaRepository;
import com.pontoeletronico.backend.repository.UsuarioRepository;
import com.pontoeletronico.backend.dto.AlterarSenhaDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final HistoricoSenhaRepository historicoRepository;

    @Value("${admin.codigo}")
    private String adminCode;

    public UsuarioService(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder,
            HistoricoSenhaRepository historicoRepository
    ) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.historicoRepository = historicoRepository;
    }

    public Optional<Usuario> findByEmail(String email) {

        return repository.findByEmail(email);
    }

    public Optional<Usuario> findById(Long id) {

        return repository.findById(id);
    }

    public Usuario salvar(RegisterRequestDTO dto) {

        if (repository.findByEmail(dto.getEmail()).isPresent()) {

            throw new RuntimeException(
                    "Email já cadastrado"
            );
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

        validarSenhaForte(dto.getSenha());

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        usuario.setSenha(
                passwordEncoder.encode(dto.getSenha())
        );

        usuario.setRole(dto.getRole());

        Usuario usuarioSalvo =
                repository.save(usuario);

        HistoricoSenha historico =
                new HistoricoSenha();

        historico.setUsuario(usuarioSalvo);

        historico.setSenhaHash(
                usuarioSalvo.getSenha()
        );

        historico.setDataAlteracao(
                LocalDateTime.now()
        );

        historicoRepository.save(historico);

        return usuarioSalvo;
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

        // verifica bloqueio
        if (
                usuario.getBloqueadoAte() != null &&
                usuario.getBloqueadoAte()
                        .isAfter(LocalDateTime.now())
        ) {

            throw new RuntimeException(
                    "Usuário bloqueado por 10 minutos"
            );
        }

        boolean senhaCorreta =
                passwordEncoder.matches(
                        senha,
                        usuario.getSenha()
                );

        if (!senhaCorreta) {

            usuario.setTentativasFalhas(
                    usuario.getTentativasFalhas() + 1
            );

            if (usuario.getTentativasFalhas() >= 5) {

                usuario.setBloqueadoAte(
                        LocalDateTime.now()
                                .plusMinutes(10)
                );

                usuario.setTentativasFalhas(0);
            }

            repository.save(usuario);

            return false;
        }

        // login correto
        usuario.setTentativasFalhas(0);

        repository.save(usuario);

        return true;
    }

    private void validarSenhaForte(String senha) {

        if (senha.length() < 10) {

            throw new RuntimeException(
                    "A senha deve ter no mínimo 10 caracteres"
            );
        }

        boolean possuiMaiuscula =
                senha.matches(".*[A-Z].*");

        boolean possuiNumero =
                senha.matches(".*\\d.*");

        boolean possuiEspecial =
                senha.matches(".*[^a-zA-Z0-9].*");

        boolean possuiLetra =
                senha.matches(".*[a-zA-Z].*");

        if (
                !possuiMaiuscula ||
                !possuiNumero ||
                !possuiEspecial ||
                !possuiLetra
        ) {

            throw new RuntimeException(
                    "A senha deve conter letra maiúscula, número e caractere especial"
            );
        }
    }

    private void validarHistoricoSenha(
            Usuario usuario,
            String novaSenha
    ) {

        List<HistoricoSenha> ultimasSenhas =
                historicoRepository
                        .findTop3ByUsuarioOrderByDataAlteracaoDesc(
                                usuario
                        );

        for (HistoricoSenha historico : ultimasSenhas) {

            boolean mesmaSenha =
                    passwordEncoder.matches(
                            novaSenha,
                            historico.getSenhaHash()
                    );

            if (mesmaSenha) {

                throw new RuntimeException(
                        "Você não pode reutilizar as últimas 3 senhas"
                );
            }
        }
    }

    public void alterarSenha(
            AlterarSenhaDTO dto
    ) {

        Usuario usuario =
                repository.findByEmail(dto.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Usuário não encontrado"
                                )
                        );

        // valida senha atual
        boolean senhaAtualCorreta =
                passwordEncoder.matches(
                        dto.getSenhaAtual(),
                        usuario.getSenha()
                );

        if (!senhaAtualCorreta) {

            throw new RuntimeException(
                    "Senha atual incorreta"
            );
        }

        // valida senha forte
        validarSenhaForte(dto.getNovaSenha());

        // valida histórico
        validarHistoricoSenha(
                usuario,
                dto.getNovaSenha()
        );

        // gera hash da nova senha
        String novaSenhaHash =
                passwordEncoder.encode(
                        dto.getNovaSenha()
                );

        // atualiza senha
        usuario.setSenha(novaSenhaHash);

        repository.save(usuario);

        // salva histórico
        HistoricoSenha historico =
                new HistoricoSenha();

        historico.setUsuario(usuario);

        historico.setSenhaHash(novaSenhaHash);

        historico.setDataAlteracao(
                LocalDateTime.now()
        );

        historicoRepository.save(historico);
    }
}