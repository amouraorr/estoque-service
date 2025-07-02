package com.fiap.cliente.usecase;

import com.fiap.cliente.domain.Estoque;
import com.fiap.cliente.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaixarEstoqueUseCase {
    private final EstoqueGateway gateway;

    public Estoque execute(String sku, int quantidade) {
        return gateway.baixarEstoque(sku, quantidade);
    }
}
