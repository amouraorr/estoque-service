package com.fiap.estoque.usecase.service;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarEstoqueServiceUseCaseTest {

    @Mock
    private EstoqueGateway gateway;

    @Mock
    private Logger logger;

    @InjectMocks
    private AtualizarEstoqueServiceUseCase service;

    private Estoque estoqueEntrada;
    private Estoque estoqueAtualizado;

    @BeforeEach
    void setUp() {
        estoqueEntrada = Estoque.builder()
                .id(1L)
                .sku("SKU-123")
                .quantidadeDisponivel(10)
                .build();

        estoqueAtualizado = Estoque.builder()
                .id(1L)
                .sku("SKU-123")
                .quantidadeDisponivel(15)
                .build();
    }

    @Test
    void deveAtualizarEstoqueComSucesso() {
        // Given
        when(gateway.atualizar(estoqueEntrada)).thenReturn(estoqueAtualizado);

        // When
        Estoque resultado = service.execute(estoqueEntrada);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getSku()).isEqualTo("SKU-123");
        assertThat(resultado.getQuantidadeDisponivel()).isEqualTo(15);

        verify(gateway).atualizar(estoqueEntrada);
        verifyNoMoreInteractions(gateway);
    }

    @Test
    void deveVerificarChamadaCorretaAoGateway() {
        // Given
        when(gateway.atualizar(any(Estoque.class))).thenReturn(estoqueAtualizado);

        // When
        service.execute(estoqueEntrada);

        // Then
        verify(gateway, times(1)).atualizar(eq(estoqueEntrada));
    }

    @Test
    void deveRetornarEstoqueAtualizadoPeloGateway() {
        // Given
        Estoque estoqueEsperado = Estoque.builder()
                .id(2L)
                .sku("SKU-456")
                .quantidadeDisponivel(25)
                .build();

        when(gateway.atualizar(estoqueEntrada)).thenReturn(estoqueEsperado);

        // When
        Estoque resultado = service.execute(estoqueEntrada);

        // Then
        assertThat(resultado).isSameAs(estoqueEsperado);
        assertThat(resultado.getId()).isEqualTo(2L);
        assertThat(resultado.getSku()).isEqualTo("SKU-456");
        assertThat(resultado.getQuantidadeDisponivel()).isEqualTo(25);
    }

    @Test
    void devePropagExcecaoQuandoGatewayFalhar() {
        // Given
        RuntimeException excecaoEsperada = new RuntimeException("Erro no banco de dados");
        when(gateway.atualizar(estoqueEntrada)).thenThrow(excecaoEsperada);

        // When & Then
        assertThatThrownBy(() -> service.execute(estoqueEntrada))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erro no banco de dados");

        verify(gateway).atualizar(estoqueEntrada);
    }

    @Test
    void deveProcessarEstoqueComValoresLimite() {
        // Given
        Estoque estoqueZero = Estoque.builder()
                .id(1L)
                .sku("SKU-ZERO")
                .quantidadeDisponivel(0)
                .build();

        Estoque estoqueAtualizadoZero = Estoque.builder()
                .id(1L)
                .sku("SKU-ZERO")
                .quantidadeDisponivel(0)
                .build();

        when(gateway.atualizar(estoqueZero)).thenReturn(estoqueAtualizadoZero);

        // When
        Estoque resultado = service.execute(estoqueZero);

        // Then
        assertThat(resultado.getQuantidadeDisponivel()).isEqualTo(0);
        verify(gateway).atualizar(estoqueZero);
    }

    @Test
    void deveProcessarEstoqueComQuantidadeAlta() {
        // Given
        Estoque estoqueAlto = Estoque.builder()
                .id(1L)
                .sku("SKU-ALTO")
                .quantidadeDisponivel(99999)
                .build();

        when(gateway.atualizar(estoqueAlto)).thenReturn(estoqueAlto);

        // When
        Estoque resultado = service.execute(estoqueAlto);

        // Then
        assertThat(resultado.getQuantidadeDisponivel()).isEqualTo(99999);
    }

    @Test
    void deveProcessarEstoqueComSkuNulo() {
        // Given
        Estoque estoqueSkuNulo = Estoque.builder()
                .id(1L)
                .sku(null)
                .quantidadeDisponivel(10)
                .build();

        when(gateway.atualizar(estoqueSkuNulo)).thenReturn(estoqueSkuNulo);

        // When
        Estoque resultado = service.execute(estoqueSkuNulo);

        // Then
        assertThat(resultado.getSku()).isNull();
        verify(gateway).atualizar(estoqueSkuNulo);
    }

    @Test
    void deveManterIdOriginalDoEstoque() {
        // Given
        Long idOriginal = 999L;
        estoqueEntrada.setId(idOriginal);
        estoqueAtualizado.setId(idOriginal);

        when(gateway.atualizar(estoqueEntrada)).thenReturn(estoqueAtualizado);

        // When
        Estoque resultado = service.execute(estoqueEntrada);

        // Then
        assertThat(resultado.getId()).isEqualTo(idOriginal);
    }

    @Test
    void deveVerificarComportamentoComEstoqueVazio() {
        // Given
        Estoque estoqueVazio = new Estoque();
        when(gateway.atualizar(estoqueVazio)).thenReturn(estoqueVazio);

        // When
        Estoque resultado = service.execute(estoqueVazio);

        // Then
        assertThat(resultado).isNotNull();
        verify(gateway).atualizar(estoqueVazio);
    }
}