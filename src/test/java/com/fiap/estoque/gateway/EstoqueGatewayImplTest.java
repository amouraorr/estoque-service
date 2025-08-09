package com.fiap.estoque.gateway;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.entity.EstoqueEntity;
import com.fiap.estoque.mapper.EstoqueMapper;
import com.fiap.estoque.repository.EstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueGatewayImplTest {

    @Mock
    private EstoqueRepository repository;

    @Mock
    private EstoqueMapper mapper;

    @InjectMocks
    private EstoqueGatewayImpl estoqueGateway;

    private EstoqueEntity estoqueEntity;
    private Estoque estoqueDomain;
    private static final String SKU_TESTE = "SKU123";
    private static final int QUANTIDADE_DISPONIVEL = 100;
    private static final int QUANTIDADE_BAIXA = 10;

    @BeforeEach
    void setUp() {
        estoqueEntity = criarEstoqueEntity();
        estoqueDomain = criarEstoqueDomain();
    }

    @Test
    void buscarPorSku_deveRetornarEstoqueQuandoEncontrado() {
        // Given
        when(repository.findBySku(SKU_TESTE)).thenReturn(Optional.of(estoqueEntity));
        when(mapper.toDomain(estoqueEntity)).thenReturn(estoqueDomain);

        // When
        Optional<Estoque> resultado = estoqueGateway.buscarPorSku(SKU_TESTE);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals(estoqueDomain, resultado.get());
        verify(repository).findBySku(SKU_TESTE);
        verify(mapper).toDomain(estoqueEntity);
    }

    @Test
    void buscarPorSku_deveRetornarVazioQuandoNaoEncontrado() {
        // Given
        when(repository.findBySku(SKU_TESTE)).thenReturn(Optional.empty());

        // When
        Optional<Estoque> resultado = estoqueGateway.buscarPorSku(SKU_TESTE);

        // Then
        assertTrue(resultado.isEmpty());
        verify(repository).findBySku(SKU_TESTE);
        verify(mapper, never()).toDomain(any(EstoqueEntity.class));
    }

    @Test
    void atualizar_deveAtualizarEstoqueComSucesso() {
        // Given
        when(mapper.toEntity(estoqueDomain)).thenReturn(estoqueEntity);
        when(repository.save(estoqueEntity)).thenReturn(estoqueEntity);
        when(mapper.toDomain(estoqueEntity)).thenReturn(estoqueDomain);

        // When
        Estoque resultado = estoqueGateway.atualizar(estoqueDomain);

        // Then
        assertEquals(estoqueDomain, resultado);
        verify(mapper).toEntity(estoqueDomain);
        verify(repository).save(estoqueEntity);
        verify(mapper).toDomain(estoqueEntity);
    }

    @Test
    void listarTodos_deveRetornarListaVaziaQuandoNaoHaEstoques() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<Estoque> resultado = estoqueGateway.listarTodos();

        // Then
        assertTrue(resultado.isEmpty());
        verify(repository).findAll();
        verify(mapper, never()).toDomain(any(EstoqueEntity.class));
    }

    @Test
    void listarTodos_deveRetornarListaDeEstoquesQuandoExistem() {
        // Given
        List<EstoqueEntity> entidades = List.of(estoqueEntity);
        List<Estoque> dominios = List.of(estoqueDomain);

        when(repository.findAll()).thenReturn(entidades);
        when(mapper.toDomain(estoqueEntity)).thenReturn(estoqueDomain);

        // When
        List<Estoque> resultado = estoqueGateway.listarTodos();

        // Then
        assertEquals(1, resultado.size());
        assertEquals(dominios.get(0), resultado.get(0));
        verify(repository).findAll();
        verify(mapper).toDomain(estoqueEntity);
    }

    @Test
    void baixarEstoque_deveBaixarEstoqueComSucesso() {
        // Given
        EstoqueEntity entityAtualizada = criarEstoqueEntity();
        entityAtualizada.setQuantidadeDisponivel(QUANTIDADE_DISPONIVEL - QUANTIDADE_BAIXA);

        Estoque estoqueAtualizado = criarEstoqueDomain();
        estoqueAtualizado.setQuantidadeDisponivel(QUANTIDADE_DISPONIVEL - QUANTIDADE_BAIXA);

        when(repository.findBySku(SKU_TESTE)).thenReturn(Optional.of(estoqueEntity));
        when(repository.save(any(EstoqueEntity.class))).thenReturn(entityAtualizada);
        when(mapper.toDomain(entityAtualizada)).thenReturn(estoqueAtualizado);

        // When
        Estoque resultado = estoqueGateway.baixarEstoque(SKU_TESTE, QUANTIDADE_BAIXA);

        // Then
        assertEquals(estoqueAtualizado, resultado);
        assertEquals(QUANTIDADE_DISPONIVEL - QUANTIDADE_BAIXA, resultado.getQuantidadeDisponivel());
        verify(repository).findBySku(SKU_TESTE);
        verify(repository).save(any(EstoqueEntity.class));
        verify(mapper).toDomain(entityAtualizada);
    }

    @Test
    void baixarEstoque_deveLancarExcecaoQuandoEstoqueNaoEncontrado() {
        // Given
        when(repository.findBySku(SKU_TESTE)).thenReturn(Optional.empty());

        // When
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> estoqueGateway.baixarEstoque(SKU_TESTE, QUANTIDADE_BAIXA));

        assertEquals("Estoque não encontrado para SKU: " + SKU_TESTE, exception.getMessage());
        verify(repository).findBySku(SKU_TESTE);
        verify(repository, never()).save(any(EstoqueEntity.class));
        verify(mapper, never()).toDomain(any(EstoqueEntity.class));
    }

    @Test
    void baixarEstoque_deveLancarExcecaoQuandoEstoqueInsuficiente() {
        // Given
        estoqueEntity.setQuantidadeDisponivel(5);
        when(repository.findBySku(SKU_TESTE)).thenReturn(Optional.of(estoqueEntity));

        // When
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> estoqueGateway.baixarEstoque(SKU_TESTE, QUANTIDADE_BAIXA));

        assertEquals("Estoque insuficiente para SKU: " + SKU_TESTE, exception.getMessage());
        verify(repository).findBySku(SKU_TESTE);
        verify(repository, never()).save(any(EstoqueEntity.class));
        verify(mapper, never()).toDomain(any(EstoqueEntity.class));
    }

    @Test
    void baixarEstoque_devePermitirBaixaComQuantidadeExata() {
        // Given
        estoqueEntity.setQuantidadeDisponivel(QUANTIDADE_BAIXA);
        EstoqueEntity entityAtualizada = criarEstoqueEntity();
        entityAtualizada.setQuantidadeDisponivel(0);

        Estoque estoqueAtualizado = criarEstoqueDomain();
        estoqueAtualizado.setQuantidadeDisponivel(0);

        when(repository.findBySku(SKU_TESTE)).thenReturn(Optional.of(estoqueEntity));
        when(repository.save(any(EstoqueEntity.class))).thenReturn(entityAtualizada);
        when(mapper.toDomain(entityAtualizada)).thenReturn(estoqueAtualizado);

        // When
        Estoque resultado = estoqueGateway.baixarEstoque(SKU_TESTE, QUANTIDADE_BAIXA);

        // Then
        assertEquals(0, resultado.getQuantidadeDisponivel());
        verify(repository).findBySku(SKU_TESTE);
        verify(repository).save(any(EstoqueEntity.class));
        verify(mapper).toDomain(entityAtualizada);
    }

    private EstoqueEntity criarEstoqueEntity() {
        EstoqueEntity entity = new EstoqueEntity();
        entity.setId(1L);
        entity.setSku(SKU_TESTE);
        entity.setQuantidadeDisponivel(QUANTIDADE_DISPONIVEL);
        return entity;
    }

    private Estoque criarEstoqueDomain() {
        return Estoque.builder()
                .id(1L)
                .sku(SKU_TESTE)
                .quantidadeDisponivel(QUANTIDADE_DISPONIVEL)
                .build();
    }
}