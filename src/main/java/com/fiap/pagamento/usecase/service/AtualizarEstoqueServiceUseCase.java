package com.fiap.pagamento.usecase.service;

import com.fiap.pagamento.domain.Estoque;
import com.fiap.pagamento.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarEstoqueServiceUseCase {
    private final EstoqueGateway gateway;

    public Estoque execute(Estoque estoque) {
        return gateway.atualizar(estoque);
    }
}
