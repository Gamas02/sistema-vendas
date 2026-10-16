package br.com.aweb.sistema_vendas.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.aweb.sistema_vendas.model.UsuarioRole;
import br.com.aweb.sistema_vendas.repository.UsuarioRepository;
import br.com.aweb.sistema_vendas.service.UsuarioService;

// Cria os usuários iniciais quando a tabela está vazia (necessário para o primeiro login)
@Component
public class UsuariosIniciais implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UsuariosIniciais.class);

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Value("${ADMIN_EMAIL:admin@vendas.com}")
    private String adminEmail;

    @Value("${ADMIN_SENHA:admin123}")
    private String adminSenha;

    @Value("${PADRAO_EMAIL:padrao@vendas.com}")
    private String padraoEmail;

    @Value("${PADRAO_SENHA:padrao123}")
    private String padraoSenha;

    public UsuariosIniciais(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return;
        }
        usuarioService.cadastrar("Administrador", adminEmail, adminSenha, UsuarioRole.ADMIN);
        usuarioService.cadastrar("Usuário Padrão", padraoEmail, padraoSenha, UsuarioRole.PADRAO);
        log.info("Usuários iniciais criados: {} (ADMIN) e {} (PADRAO)", adminEmail, padraoEmail);
    }
}
