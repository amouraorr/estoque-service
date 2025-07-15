package com.fiap.estoque.usecase.service;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizarEstoqueServiceUseCase {
    private final EstoqueGateway gateway;

    public Estoque execute(Estoque estoque) {
        log.info("Executando atualização de estoque para SKU: {}", estoque.getSku());
        Estoque atualizado = gateway.atualizar(estoque);
        log.info("Estoque atualizado com sucesso para SKU: {}", estoque.getSku());
        return atualizado;
    }
}