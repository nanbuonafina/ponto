package com.pontoeletronico.backend.service.backup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pontoeletronico.backend.model.RegistroPonto;
import com.pontoeletronico.backend.model.Usuario;
import com.pontoeletronico.backend.repository.RegistroPontoRepository;
import com.pontoeletronico.backend.repository.UsuarioRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BackupService {

    private static final String DIRETORIO_BACKUP = "backups/";

    private final UsuarioRepository usuarioRepository;
    private final RegistroPontoRepository registroRepository;
    private final ObjectMapper objectMapper;


    public BackupService(UsuarioRepository usuarioRepository,
                        RegistroPontoRepository registroRepository,
                        ObjectMapper objectMapper) {
        this.usuarioRepository = usuarioRepository;
        this.registroRepository = registroRepository;
        this.objectMapper = objectMapper;
    }

    // coleta todos os registros do banco e coloca num objeto Map e transforma esse objeto em um json
    private void gerarBackupArquivo(String caminho) throws Exception {
        Map<String, Object> dados = new HashMap<>();

        dados.put("usuarios", usuarioRepository.findAll());
        dados.put("registros", registroRepository.findAll());

        objectMapper.writeValue(new File(caminho), dados);
    }    

    // gatilho manual que define um nome padrao e chama o metodo de geracao de arquivo
    public String realizarBackup() {
        try {
            String caminho = DIRETORIO_BACKUP + "backup_manual.json";
            gerarBackupArquivo(caminho);
            return "Backup manual realizado com sucesso!";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar backup", e);
        }
    }

    // lê um arquivo json, apaga tudo o que existe no banco de dados atual e reinsere os dados do arquivo
    public String restaurarBackup(String nomeArquivo) {
        try {
            // validacao de seguranca do nome do arquivo
            if (!nomeArquivo.startsWith("backup_") || !nomeArquivo.endsWith(".json")) {
                throw new RuntimeException("Arquivo inválido");
            }

            File arquivo = new File(DIRETORIO_BACKUP + nomeArquivo);

            if (!arquivo.exists()) {
                throw new RuntimeException("Arquivo de backup não encontrado: " + nomeArquivo);
            }

            // le o json e transforma de volta em um mapa de objetos
            Map<String, Object> dados = objectMapper.readValue(
                    arquivo,
                    new TypeReference<Map<String, Object>>() {}
            );

            // extrai a lista de usuarios do mapa
            List<Usuario> usuarios = objectMapper.convertValue(
                    dados.get("usuarios"),
                    new TypeReference<List<Usuario>>() {}
            );

            // extrai a lista de registos de ponto do mapa
            List<RegistroPonto> registros = objectMapper.convertValue(
                    dados.get("registros"),
                    new TypeReference<List<RegistroPonto>>() {}
            );

            // limpa antes de restaurar
            registroRepository.deleteAll();
            usuarioRepository.deleteAll();

            // limpando ids
            usuarios.forEach(u -> u.setId(null));
            registros.forEach(r -> r.setId(null));
            
            // salva primeiro os usuarios (ja que os registros dependem deles)
            List<Usuario> usuariosSalvos = usuarioRepository.saveAll(usuarios);

            // mapa de consulta por email
            Map<String, Usuario> usuarioMap = usuariosSalvos.stream()
                    .collect(Collectors.toMap(Usuario::getEmail, u -> u));

            // vincula os registros aos seus respectivos usuario atraves do email        
            for (RegistroPonto r : registros) {
                String email = r.getUsuario().getEmail();
                r.setUsuario(usuarioMap.get(email));
            }

            registroRepository.saveAll(registros);

            return "Backup restaurado com sucesso a partir de: " + nomeArquivo;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao restaurar backup", e);
        }
    }


    // agenda o backup para cada uma hora
    @Scheduled(cron = "0 0 * * * *")
    public void backupAutomatico() {
        try {

            // prefixo e sufixo para a nomear o arquivo
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String caminho = DIRETORIO_BACKUP + "backup_auto_" + timestamp + ".json";

            gerarBackupArquivo(caminho);

            System.out.println("Backup automático executado: " + caminho);
        } catch (Exception e) {
            System.out.println("Erro no backup automático");
        }
    }

    // listar os backups disponiveis a partir da pasta de backups
    public List<String> listarBackups() {
        File pasta = new File(DIRETORIO_BACKUP);

        // retorna lista vazia se a pasta estiver vazia
        if (!pasta.exists()) {
            return List.of();
        }

        // pega todos os arquivos da pasta, filtra os que começam com "backup" e terminam com "json", pega apenas os nomes e transforma em uma lista
        return List.of(pasta.listFiles())
                .stream()
                .filter(f -> f.getName().startsWith("backup_"))
                .filter(f -> f.getName().endsWith(".json"))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}