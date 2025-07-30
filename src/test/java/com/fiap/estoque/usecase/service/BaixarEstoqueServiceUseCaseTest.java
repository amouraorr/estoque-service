package com.fiap.estoque.usecase.service;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaixarEstoqueServiceUseCaseTest {

    @Mock
    private EstoqueGateway gateway;

    @InjectMocks
    private BaixarEstoqueServiceUseCase service;

    private String skuPadrao;
    private int quantidadePadrao;
    private Estoque estoqueAtualizado;

    @BeforeEach
    void setUp() {
        skuPadrao = "SKU-123";
        quantidadePadrao = 5;
        estoqueAtualizado = Estoque.builder()
                .id(1L)
                .sku(skuPadrao)
                .quantidadeDisponivel(15)
                .build();
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        // Given
        when(gateway.baixarEstoque(skuPadrao, quantidadePadrao)).thenReturn(estoqueAtualizado);

        // When
        Estoque resultado = service.execute(skuPadrao, quantidadePadrao);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getSku()).isEqualTo(skuPadrao);
        assertThat(resultado.getQuantidadeDisponivel()).isEqualTo(15);

        verify(gateway).baixarEstoque(skuPadrao, quantidadePadrao);
        verifyNoMoreInteractions(gateway);
    }

    @Test
    void deveVerificarChamadaCorretaAoGateway() {
        // Given
        String sku = "SKU-456";
        int quantidade = 10;
        when(gateway.baixarEstoque(anyString(), anyInt())).thenReturn(estoqueAtualizado);

        // When
        service.execute(sku, quantidade);

        // Then
        verify(gateway, times(1)).baixarEstoque(eq(sku), eq(quantidade));
    }

    @Test
    void deveRetornarEstoqueAtualizadoPeloGateway() {
        // Given
        Estoque estoqueEsperado = Estoque.builder()
                .id(2L)
                .sku("SKU-789")
                .quantidadeDisponivel(8)
                .build();

        when(gateway.baixarEstoque(skuPadrao, quantidadePadrao)).thenReturn(estoqueEsperado);

        // When
        Estoque resultado = service.execute(skuPadrao, quantidadePadrao);

        // Then
        assertThat(resultado).isSameAs(estoqueEsperado);
        assertThat(resultado.getId()).isEqualTo(2L);
        assertThat(resultado.getSku()).isEqualTo("SKU-789");
        assertThat(resultado.getQuantidadeDisponivel()).isEqualTo(8);
    }

    @Test
    void devePropagExcecaoQuandoGatewayFalhar() {
        // Given
        RuntimeException excecaoEsperada = new RuntimeException("Estoque insuficiente");
        when(gateway.baixarEstoque(skuPadrao, quantidadePadrao)).thenThrow(excecaoEsperada);

        // When
        assertThatThrownBy(() -> service.execute(skuPadrao, quantidadePadrao))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Estoque insuficiente");

        verify(gateway).baixarEstoque(skuPadrao, quantidadePadrao);
    }

    @Test
    void deveBaixarEstoqueComQuantidadeUm() {
        // Given
        int quantidadeUm = 1;
        Estoque estoqueResultado = Estoque.builder()
                .id(1L)
                .sku(skuPadrao)
                .quantidadeDisponivel(19)
                .build();

        when(gateway.baixarEstoque(skuPadrao, quantidadeUm)).thenReturn(estoqueResultado);

        // When
        Estoque resultado = service.execute(skuPadrao, quantidadeUm);

        // Then
        assertThat(resultado.getQuantidadeDisponivel()).isEqualTo(19);
        verify(gateway).baixarEstoque(skuPadrao, quantidadeUm);
    }

    @Test
    void deveBaixarEstoqueComQuantidadeAlta() {
        // Given
        int quantidadeAlta = 100;
        Estoque estoqueResultado = Estoque.builder()
                .id(1L)
                .sku(skuPadrao)
                .quantidadeDisponivel(0)
                .build();

        when(gateway.baixarEstoque(skuPadrao, quantidadeAlta)).thenReturn(estoqueResultado);

        // When
        Estoque resultado = service.execute(skuPadrao, quantidadeAlta);

        // Then
        assertThat(resultado.getQuantidadeDisponivel()).isEqualTo(0);
        verify(gateway).baixarEstoque(skuPadrao, quantidadeAlta);
    }

    @Test
    void deveBaixarEstoqueComQuantidadeZero() {
        // Given
        int quantidadeZero = 0;
        when(gateway.baixarEstoque(skuPadrao, quantidadeZero)).thenReturn(estoqueAtualizado);

        // When
        Estoque resultado = service.execute(skuPadrao, quantidadeZero);

        // Then
        assertThat(resultado).isNotNull();
        verify(gateway).baixarEstoque(skuPadrao, quantidadeZero);
    }

    @Test
    void deveProcessarSkuNulo() {
        // Given
        String skuNulo = null;
        when(gateway.baixarEstoque(skuNulo, quantidadePadrao)).thenReturn(estoqueAtualizado);

        // When
        Estoque resultado = service.execute(skuNulo, quantidadePadrao);

        // Then
        assertThat(resultado).isNotNull();
        verify(gateway).baixarEstoque(skuNulo, quantidadePadrao);
    }

    @Test
    void deveProcessarSkuVazio() {
        // Given
        String skuVazio = "";
        when(gateway.baixarEstoque(skuVazio, quantidadePadrao)).thenReturn(estoqueAtualizado);

        // When
        Estoque resultado = service.execute(skuVazio, quantidadePadrao);

        // Then
        assertThat(resultado).isNotNull();
        verify(gateway).baixarEstoque(skuVazio, quantidadePadrao);
    }

    @Test
    void deveBaixarEstoqueComQuantidadeNegativa() {
        // Given
        int quantidadeNegativa = -5;
        when(gateway.baixarEstoque(skuPadrao, quantidadeNegativa)).thenReturn(estoqueAtualizado);

        // When
        Estoque resultado = service.execute(skuPadrao, quantidadeNegativa);

        // Then
        assertThat(resultado).isNotNull();
        verify(gateway).baixarEstoque(skuPadrao, quantidadeNegativa);
    }

    @Test
    void deveManterSkuOriginalNoRetorno() {
        // Given
        String skuEspecifico = "SKU-ESPECIAL-123";
        Estoque estoqueComSkuEspecifico = Estoque.builder()
                .id(1L)
                .sku(skuEspecifico)
                .quantidadeDisponivel(25)
                .build();

        when(gateway.baixarEstoque(skuEspecifico, quantidadePadrao)).thenReturn(estoqueComSkuEspecifico);

        // When
        Estoque resultado = service.execute(skuEspecifico, quantidadePadrao);

        // Then
        assertThat(resultado.getSku()).isEqualTo(skuEspecifico);
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        // Given
        IllegalArgumentException excecaoEstoqueInsuficiente =
                new IllegalArgumentException("Quantidade solicitada maior que disponível");
        when(gateway.baixarEstoque(skuPadrao, quantidadePadrao)).thenThrow(excecaoEstoqueInsuficiente);

        // When
        assertThatThrownBy(() -> service.execute(skuPadrao, quantidadePadrao))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantidade solicitada maior que disponível");
    }

    @Test
    void deveProcessarDiferentesQuantidadesCorretamente() {
        // Given
        int[] quantidades = {1, 5, 10, 25, 50};

        for (int quantidade : quantidades) {
            Estoque estoqueEsperado = Estoque.builder()
                    .id(1L)
                    .sku(skuPadrao)
                    .quantidadeDisponivel(100 - quantidade)
                    .build();

            when(gateway.baixarEstoque(skuPadrao, quantidade)).thenReturn(estoqueEsperado);

            // When
            Estoque resultado = service.execute(skuPadrao, quantidade);

            // Then
            assertThat(resultado.getQuantidadeDisponivel()).isEqualTo(100 - quantidade);
        }

        verify(gateway, times(quantidades.length)).baixarEstoque(eq(skuPadrao), anyInt());
    }
}