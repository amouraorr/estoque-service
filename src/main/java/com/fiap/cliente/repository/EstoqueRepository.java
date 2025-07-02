package com.fiap.cliente.repository;

import com.fiap.cliente.entity.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long> {
    Optional<EstoqueEntity> findBySku(String sku);
}
