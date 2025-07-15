package com.fiap.estoque.gateway;

import com.fiap.estoque.domain.Estoque;

import java.util.Optional;
import java.util.List;

public interface EstoqueGateway {

    Optional<Estoque> buscarPorSku(String sku);
    Estoque atualizar(Estoque estoque);
    List<Estoque> listarTodos();
    Estoque baixarEstoque(String sku, int quantidade);
}
