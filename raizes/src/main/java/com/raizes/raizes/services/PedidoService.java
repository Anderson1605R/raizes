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
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodo(dto.formaPagamento());

        pagamento.setStatusPagamento(StatusPagamentoEnum.APROVADO);

        pedido.setPagamento(pagamento);

        // Atualiza o status do pedido porque o pagamento mock foi aprovado
        pedido.setStatus(StatusPedidoEnum.PREPARO);

        System.out.println(
                "AUDITORIA: Novo pedido criado com sucesso pelo cliente " + emailCliente + " via " + dto.canalPedido());

        return pedidoRepository.save(pedido);
    }
}