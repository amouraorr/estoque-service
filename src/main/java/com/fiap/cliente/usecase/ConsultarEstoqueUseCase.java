package com.fiap.cliente.usecase;

import com.fiap.cliente.domain.Estoque;
import com.fiap.cliente.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarEstoqueUseCase {
    private final EstoqueGateway gateway;

    public Optional<Estoque> execute(String sku) {
        return gateway.buscarPorSku(sku);
    }
}
