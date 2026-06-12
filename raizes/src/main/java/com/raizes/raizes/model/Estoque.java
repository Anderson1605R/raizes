package com.raizes.raizes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_estoque")
public class Estoque {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapeamento da relação com a Unidade (A Chave Estrangeira unidade_id)
    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    // Mapeamento da relação com o Produto (A Chave Estrangeira produto_id)
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Campo vital para a regra de negócio do controle descentralizado
    @Column(nullable = false)
    private Integer quantidade;
   
    public Estoque() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
