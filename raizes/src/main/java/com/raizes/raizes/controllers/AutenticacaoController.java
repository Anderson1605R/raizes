package com.raizes.raizes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raizes.raizes.config.TokenService;
import com.raizes.raizes.dto.LoginRequestDTO;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO data) {
        // Junta o email e senha digitados
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

        // O Spring Security compara o BCrypt com o banco de dados aqui
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Se a senha estiver certa, gera o token JWT
        var token = tokenService.gerarToken(auth.getName());

        // Devolve o token para o cliente
        return ResponseEntity.ok(token);
    }
}
