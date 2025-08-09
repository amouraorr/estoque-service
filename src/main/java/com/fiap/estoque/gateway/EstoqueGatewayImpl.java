package com.fiap.estoque.gateway;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.entity.EstoqueEntity;
import com.fiap.estoque.mapper.EstoqueMapper;
import com.fiap.estoque.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class EstoqueGatewayImpl implements EstoqueGateway {

    private final EstoqueRepository repository;
    private final EstoqueMapper mapper;

    @Override
    public Optional<Estoque> buscarPorSku(String sku) {
        log.debug("Buscando estoque no banco para SKU: {}", sku);
        return repository.findBySku(sku).map(mapper::toDomain);
    }

    @Override
    public Estoque atualizar(Estoque estoque) {
        log.debug("Atualizando estoque no banco para SKU: {}", estoque.getSku());
        EstoqueEntity entity = mapper.toEntity(estoque);
        Estoque atualizado = mapper.toDomain(repository.save(entity));
        log.debug("Estoque atualizado no banco para SKU: {}", estoque.getSku());
        return atualizado;
    }

    @Override
    public List<Estoque> listarTodos() {
        log.debug("Listando todos os estoques");
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Estoque baixarEstoque(String sku, int quantidade) {
        log.debug("Baixando estoque no banco para SKU: {}, quantidade: {}", sku, quantidade);
        EstoqueEntity entity = repository.findBySku(sku)
                .orElseThrow(() -> {
                    log.warn("Estoque não encontrado no banco para SKU: {}", sku);
                    return new RuntimeException("Estoque não encontrado para SKU: " + sku);
                });
        if (entity.getQuantidadeDisponivel() < quantidade) {
            log.warn("Estoque insuficiente para SKU: {}. Disponível: {}, solicitado: {}", sku, entity.getQuantidadeDisponivel(), quantidade);
            throw new RuntimeException("Estoque insuficiente para SKU: " + sku);
        }
        entity.setQuantidadeDisponivel(entity.getQuantidadeDisponivel() - quantidade);
        Estoque atualizado = mapper.toDomain(repository.save(entity));
        log.debug("Estoque atualizado após baixa para SKU: {}, quantidade disponível: {}", sku, atualizado.getQuantidadeDisponivel());
        return atualizado;
    }
}
