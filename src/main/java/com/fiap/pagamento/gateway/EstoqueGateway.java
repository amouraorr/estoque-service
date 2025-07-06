package com.fiap.pagamento.gateway;

import com.fiap.pagamento.domain.Estoque;

import java.util.Optional;
import java.util.List;

public interface EstoqueGateway {

    Optional<Estoque> buscarPorSku(String sku);
    Estoque atualizar(Estoque estoque);
    List<Estoque> listarTodos();
    Estoque baixarEstoque(String sku, int quantidade);
}
