package com.raizes.raizes.services;

import com.raizes.raizes.dto.ItemPedidoRequestDTO;
import com.raizes.raizes.dto.PedidoRequestDTO;
import com.raizes.raizes.model.*;
import com.raizes.raizes.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository,
            UnidadeRepository unidadeRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.unidadeRepository = unidadeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Pedido criarPedido(PedidoRequestDTO dto, String emailCliente) {

        // Busca o Cliente logado no banco de dados
        Usuario cliente = (Usuario) usuarioRepository.findByEmail(emailCliente);
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }

        // Busca a Unidade (Lanchonete) escolhida
        Unidade unidade = unidadeRepository.findById(dto.unidadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrada"));

        // Monta o Pedido Base
        Pedido pedido = new Pedido();
        pedido.setUsuario(cliente);
        pedido.setUnidade(unidade);
        pedido.setCanalPedido(dto.canalPedido());
        pedido.setStatus(StatusPedidoEnum.AGUARDANDO_PAGAMENTO);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setItens(new ArrayList<>());

        double valorTotal = 0.0;

        // Processa os Itens e Valida o Estoque
        for (ItemPedidoRequestDTO itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

            // Validação de Estoque (Erro 409)
            if (itemDto.quantidade() > 50) {
                throw new com.raizes.raizes.exceptions.RegraNegocioException(
                        "ESTOQUE_INSUFICIENTE: Não há quantidade suficiente para o produto " + produto.getNome());
            }

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(produto.getPrecoBase());

            valorTotal += (item.getPrecoUnitario() * item.getQuantidade());
            pedido.getItens().add(item);
        }

        pedido.setValorTotal(valorTotal);

        // Mock de Pagamento e Atualização de Status
        // PASSO 1: Cria o registro do pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodo(dto.formaPagamento());

        // PASSO 2: Cenário Negativo (O Gateway Recusou o Pagamento)
        if ("RECUSADO".equalsIgnoreCase(dto.formaPagamento())) {

            pagamento.setStatusPagamento(StatusPagamentoEnum.RECUSADO);
            pedido.setPagamento(pagamento);
            pedido.setStatus(StatusPedidoEnum.CANCELADO);
            pedidoRepository.save(pedido);

            // PASSO 3: Notifica o usuário na tela interrompendo a resposta com um Erro 400
            throw new IllegalArgumentException(
                    "Pagamento recusado pelo gateway externo. Pedido registrado com status CANCELADO.");
        }

        // PASSO 4: Cenário Positivo (O Gateway Aprovou o Pagamento)
        pagamento.setStatusPagamento(StatusPagamentoEnum.APROVADO);
        pedido.setPagamento(pagamento);

        // Atualiza a linha do tempo do pedido para a cozinha
        pedido.setStatus(StatusPedidoEnum.PREPARO);

        System.out.println(
                "AUDITORIA: Novo pedido criado com sucesso pelo cliente " + emailCliente + " via " + dto.canalPedido());

        System.out.println(
                "AUDITORIA: Novo pedido criado com sucesso pelo cliente " + emailCliente + " via " + dto.canalPedido());

        return pedidoRepository.save(pedido);
    }
}