package com.raizes.raizes.dto;

import java.util.List;

import com.raizes.raizes.model.CanalEnum;

import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(
    Long unidadeId,
    @NotNull
    CanalEnum canalPedido,
    List<ItemPedidoRequestDTO> itens,
    String formaPagamento) {
}
