package com.fiap.estoque.usecase.service;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarEstoqueServiceUseCase {
    private final EstoqueGateway gateway;

    public Optional<Estoque> execute(String sku) {
        return gateway.buscarPorSku(sku);
    }
}
