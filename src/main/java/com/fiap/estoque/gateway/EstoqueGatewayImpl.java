package com.fiap.estoque.gateway;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.entity.EstoqueEntity;
import com.fiap.estoque.mapper.EstoqueMapper;
import com.fiap.estoque.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EstoqueGatewayImpl implements EstoqueGateway {

    private final EstoqueRepository repository;
    private final EstoqueMapper mapper;

    @Override
    public Optional<Estoque> buscarPorSku(String sku) {
        return repository.findBySku(sku).map(mapper::toDomain);
    }

    @Override
    public Estoque atualizar(Estoque estoque) {
        EstoqueEntity entity = mapper.toEntity(estoque);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<Estoque> listarTodos() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Estoque baixarEstoque(String sku, int quantidade) {
        EstoqueEntity entity = repository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        if (entity.getQuantidadeDisponivel() < quantidade) {
            throw new RuntimeException("Estoque insuficiente");
        }
        entity.setQuantidadeDisponivel(entity.getQuantidadeDisponivel() - quantidade);
        return mapper.toDomain(repository.save(entity));
    }

    // Caso futuramente precise consumir eventos do Kafka, adicionar aqui métodos para processar eventos recebidos.
}
