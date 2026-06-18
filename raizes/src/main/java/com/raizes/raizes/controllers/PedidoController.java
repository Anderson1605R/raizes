package com.raizes.raizes.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raizes.raizes.dto.PedidoRequestDTO;
import com.raizes.raizes.dto.PedidoResponseDTO;
import com.raizes.raizes.model.CanalEnum;
import com.raizes.raizes.model.Pedido;
import com.raizes.raizes.repository.PedidoRepository;
import com.raizes.raizes.services.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService, PedidoRepository pedidoRepository) {
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos(
            @RequestParam(required = false) CanalEnum canalPedido) {

        List<Pedido> pedidos;

        if (canalPedido != null) {
            // Filtra os pedidos se a pessoa usar ?canalPedido=TOTEM
            pedidos = pedidoRepository.findByCanalPedido(canalPedido);
        } else {
            // Se não passar filtro, devolve todos
            pedidos = pedidoRepository.findAll();
        }

        List<PedidoResponseDTO> listaDTO = pedidos.stream()
                .map(p -> new PedidoResponseDTO(
                        p.getId(),
                        p.getStatus().toString(),
                        p.getValorTotal(),
                        p.getCanalPedido().toString(),
                        p.getDataCriacao()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaDTO);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO dto,
            Authentication authentication) {

        // Extrai o e-mail do cliente logado diretamente do Token JWT
        String emailCliente = authentication.getName();

        Pedido novoPedido = pedidoService.criarPedido(dto, emailCliente);

        PedidoResponseDTO responseDTO = new PedidoResponseDTO(
                novoPedido.getId(),
                novoPedido.getStatus().toString(),
                novoPedido.getValorTotal(),
                novoPedido.getCanalPedido().toString(),
                novoPedido.getDataCriacao());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}