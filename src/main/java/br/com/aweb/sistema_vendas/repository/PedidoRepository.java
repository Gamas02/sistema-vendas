package br.com.aweb.sistema_vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Override
    @EntityGraph(attributePaths = "cliente")
    List<Pedido> findAll();

    @EntityGraph(attributePaths = "cliente")
    List<Pedido> findByClienteId(Long clienteId);
}