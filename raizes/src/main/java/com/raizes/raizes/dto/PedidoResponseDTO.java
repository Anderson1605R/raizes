package com.raizes.raizes.dto;

import java.time.LocalDateTime;

public record PedidoResponseDTO(
    Long id,
    String status,
    Double total,
    String canalPedido,
    LocalDateTime dataCriacao) {

}
