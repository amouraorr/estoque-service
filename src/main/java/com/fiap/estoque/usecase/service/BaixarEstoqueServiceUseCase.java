package com.fiap.estoque.usecase.service;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaixarEstoqueServiceUseCase {
    private final EstoqueGateway gateway;

    public Estoque execute(String sku, int quantidade) {
        log.info("Executando baixa de estoque para SKU: {}, quantidade: {}", sku, quantidade);
        Estoque atualizado = gateway.baixarEstoque(sku, quantidade);
        log.info("Baixa de estoque realizada para SKU: {}, nova quantidade: {}", sku, atualizado.getQuantidadeDisponivel());
        return atualizado;
    }
}