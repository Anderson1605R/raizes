package com.raizes.raizes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizes.raizes.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
  
}
