package com.raizes.raizes.repository;

import com.raizes.raizes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  // O Spring Data JPA entende esse nome e cria a query SQL automaticamente!
  UserDetails findByEmail(String email);
}
