package com.fiap.estoque.usecase.service;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
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
