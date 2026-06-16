package com.raizes.raizes.dto;

import java.util.List;

import com.raizes.raizes.model.CanalEnum;

public record PedidoRequestDTO(
    Long unidadeId,
    CanalEnum canalPedido,
    List<ItemPedidoRequestDTO> itens,
    String formaPagamento) {
}
