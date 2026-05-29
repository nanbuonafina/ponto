package com.pontoeletronico.backend.service;

import com.pontoeletronico.backend.dto.RegisterRequestDTO;
import com.pontoeletronico.backend.model.HistoricoSenha;
import com.pontoeletronico.backend.model.LogTipo;
import com.pontoeletronico.backend.model.Role;
import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.repository.HistoricoSenhaRepository;
import com.pontoeletronico.backend.repository.UsuarioRepository;
import com.pontoeletronico.backend.dto.AdminAtualizarUsuarioDTO;
import com.pontoeletronico.backend.dto.AlterarSenhaDTO;
import com.pontoeletronico.backend.dto.AtualizarUsuarioDTO;
import com.pontoeletronico.backend.service.LogService;

import jakarta.transaction.Transactional;

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
    private final LogService logService;
    private final HistoricoSenhaRepository historicoSenhaRepository;;

    @Value("${admin.codigo}")
    private String adminCode;

    public UsuarioService(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder,
            HistoricoSenhaRepository historicoRepository,
            LogService logService,
            HistoricoSenhaRepository historicoSenhaRepository
    ) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.historicoRepository = historicoRepository;
        this.logService = logService;
        this.historicoSenhaRepository = historicoSenhaRepository;
    }

    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
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
        
        logService.registrar(
                LogTipo.CADASTRO_USUARIO,
                usuarioSalvo.getNome(),
                "Novo usuário cadastrado"
        );

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

            logService.registrar(
                    LogTipo.LOGIN_FALHA,
                    usuario.getEmail(),
                    "Tentativa de autenticação inválida"
            );

            usuario.setTentativasFalhas(
                    usuario.getTentativasFalhas() + 1
            );

            if (usuario.getTentativasFalhas() >= 5) {

                logService.registrar(
                        LogTipo.BLOQUEIO_USUARIO,
                        usuario.getEmail(),
                        "Usuário bloqueado por excesso de tentativas inválidas"
                );

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

        logService.registrar(
                LogTipo.LOGIN_SUCESSO,
                usuario.getEmail(),
                "Login realizado com sucesso"
        );

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

        logService.registrar(
                LogTipo.ALTERACAO_SENHA,
                usuario.getNome(),
                "Senha alterada com sucesso"
        );
    }

    public Usuario atualizarPerfil(
        String email,
        AtualizarUsuarioDTO dto
    ) {

        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado")
                );

        usuario.setNome(dto.getNome());
        usuario.setGenero(dto.getGenero());
        usuario.setDataNascimento(dto.getDataNascimento());

        Usuario atualizado = repository.save(usuario);

        logService.registrar(
                LogTipo.ALTERACAO_USUARIO,
                usuario.getNome(),
                "Perfil atualizado"
        );

        return atualizado;
    }

    @Transactional
    public void excluirUsuario(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado")
                );
        
        historicoSenhaRepository.deleteByUsuarioId(id);
        repository.delete(usuario);

        logService.registrar(
                LogTipo.EXCLUSAO_USUARIO,
                usuario.getNome(),
                "Usuário removido pelo administrador"
        );
    }

    public Usuario adminAtualizarUsuario(
        Long id,
        AdminAtualizarUsuarioDTO dto
    ) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado")
                );

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setRole(dto.getRole());

        Usuario atualizado = repository.save(usuario);

        logService.registrar(
                LogTipo.ALTERACAO_USUARIO,
                usuario.getNome(),
                "Usuário atualizado pelo administrador"
        );

        return atualizado;
    }
}