package br.com.aweb.sistema_vendas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aweb.sistema_vendas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // busca por e-mail ignorando maiúsculas/minúsculas
    Optional<Usuario> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
