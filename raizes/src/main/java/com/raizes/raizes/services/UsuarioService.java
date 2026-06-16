package com.raizes.raizes.services;

import com.raizes.raizes.dto.UsuarioRequestDTO;
import com.raizes.raizes.model.Usuario;
import com.raizes.raizes.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(UsuarioRequestDTO dto) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        
        // Encoder da senha
        novoUsuario.setSenhaHash(passwordEncoder.encode(dto.senha()));
        
        novoUsuario.setPerfil(dto.perfil());
        novoUsuario.setPontosFidelidade(0);

        return repository.save(novoUsuario);
    }
}