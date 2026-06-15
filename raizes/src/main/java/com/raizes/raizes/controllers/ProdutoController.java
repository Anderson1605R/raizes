package com.raizes.raizes.controllers;

import com.raizes.raizes.model.Produto;
import com.raizes.raizes.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        // Busca todos os produtos no banco e devolve para o cliente
        return ResponseEntity.ok(repository.findAll());
    }
}
