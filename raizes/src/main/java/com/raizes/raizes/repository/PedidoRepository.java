package com.raizes.raizes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.raizes.raizes.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
  List<Pedido> findByCanalPedido(com.raizes.raizes.model.CanalEnum canalPedido);
}
