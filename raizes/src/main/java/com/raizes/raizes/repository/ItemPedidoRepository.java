package com.raizes.raizes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizes.raizes.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
  
}
