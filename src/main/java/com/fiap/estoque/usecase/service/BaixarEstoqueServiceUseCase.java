package com.fiap.estoque.usecase.service;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaixarEstoqueServiceUseCase {
    private final EstoqueGateway gateway;

    public Estoque execute(String sku, int quantidade) {
        return gateway.baixarEstoque(sku, quantidade);
    }
}
