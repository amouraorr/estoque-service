package com.fiap.pagamento.usecase.service;

import com.fiap.pagamento.domain.Estoque;
import com.fiap.pagamento.gateway.EstoqueGateway;
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
