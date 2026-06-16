package com.raizes.raizes.dto;

import com.raizes.raizes.model.PerfilEnum;

public record UsuarioRequestDTO(
        String nome, 
        String email, 
        String senha, 
        PerfilEnum perfil
) {
  
}
