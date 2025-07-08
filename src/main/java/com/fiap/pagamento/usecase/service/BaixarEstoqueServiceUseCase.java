package com.fiap.pagamento.usecase.service;

import com.fiap.pagamento.domain.Estoque;
import com.fiap.pagamento.gateway.EstoqueGateway;
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
