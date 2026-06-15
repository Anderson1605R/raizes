package com.raizes.raizes.controllers;

import com.raizes.raizes.model.Unidade;
import com.raizes.raizes.repository.UnidadeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    private final UnidadeRepository repository;

    public UnidadeController(UnidadeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Unidade>> listarTodas() {
        return ResponseEntity.ok(repository.findAll());
    }
}
