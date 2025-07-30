
package com.fiap.estoque.usecase.service;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarEstoqueServiceUseCaseTest {

    @Mock
    private EstoqueGateway gateway;

    @InjectMocks
    private ConsultarEstoqueServiceUseCase service;

    private String skuPadrao;
    private Estoque estoquePadrao;

    @BeforeEach
    void setUp() {
        skuPadrao = "SKU-123";
        estoquePadrao = Estoque.builder()
                .id(1L)
                .sku(skuPadrao)
                .quantidadeDisponivel(50)
                .build();
    }

    @Test
    void deveRetornarEstoqueQuandoEncontrado() {
        // Given
        when(gateway.buscarPorSku(skuPadrao)).thenReturn(Optional.of(estoquePadrao));

        // When
        Optional<Estoque> resultado = service.execute(skuPadrao);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
        assertThat(resultado.get().getSku()).isEqualTo(skuPadrao);
        assertThat(resultado.get().getQuantidadeDisponivel()).isEqualTo(50);

        verify(gateway).buscarPorSku(skuPadrao);
        verifyNoMoreInteractions(gateway);
    }

    @Test
    void deveRetornarOptionalVazioQuandoNaoEncontrado() {
        // Given
        when(gateway.buscarPorSku(skuPadrao)).thenReturn(Optional.empty());

        // When
        Optional<Estoque> resultado = service.execute(skuPadrao);

        // Then
        assertThat(resultado).isEmpty();
        verify(gateway).buscarPorSku(skuPadrao);
    }

    @Test
    void deveVerificarChamadaCorretaAoGateway() {
        // Given
        String skuEspecifico = "SKU-456";
        when(gateway.buscarPorSku(anyString())).thenReturn(Optional.empty());

        // When
        service.execute(skuEspecifico);

        // Then
        verify(gateway, times(1)).buscarPorSku(eq(skuEspecifico));
    }

    @Test
    void deveRetornarExatamenteOQueOGatewayRetorna() {
        // Given
        Estoque estoqueEspecifico = Estoque.builder()
                .id(999L)
                .sku("SKU-ESPECIAL")
                .quantidadeDisponivel(25)
                .build();

        Optional<Estoque> optionalEsperado = Optional.of(estoqueEspecifico);
        when(gateway.buscarPorSku(skuPadrao)).thenReturn(optionalEsperado);

        // When
        Optional<Estoque> resultado = service.execute(skuPadrao);

        // Then
        assertThat(resultado).isSameAs(optionalEsperado);
        assertThat(resultado.get()).isSameAs(estoqueEspecifico);
    }

    @Test
    void devePropagExcecaoQuandoGatewayFalhar() {
        // Given
        RuntimeException excecaoEsperada = new RuntimeException("Erro de conexão com banco");
        when(gateway.buscarPorSku(skuPadrao)).thenThrow(excecaoEsperada);

        // When & Then
        assertThatThrownBy(() -> service.execute(skuPadrao))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erro de conexão com banco");

        verify(gateway).buscarPorSku(skuPadrao);
    }

    @Test
    void deveConsultarComSkuNulo() {
        // Given
        String skuNulo = null;
        when(gateway.buscarPorSku(skuNulo)).thenReturn(Optional.empty());

        // When
        Optional<Estoque> resultado = service.execute(skuNulo);

        // Then
        assertThat(resultado).isEmpty();
        verify(gateway).buscarPorSku(skuNulo);
    }

    @Test
    void deveConsultarComSkuVazio() {
        // Given
        String skuVazio = "";
        when(gateway.buscarPorSku(skuVazio)).thenReturn(Optional.empty());

        // When
        Optional<Estoque> resultado = service.execute(skuVazio);

        // Then
        assertThat(resultado).isEmpty();
        verify(gateway).buscarPorSku(skuVazio);
    }

    @Test
    void deveConsultarComSkuComEspacos() {
        // Given
        String skuComEspacos = "  SKU-123  ";
        when(gateway.buscarPorSku(skuComEspacos)).thenReturn(Optional.of(estoquePadrao));

        // When
        Optional<Estoque> resultado = service.execute(skuComEspacos);

        // Then
        assertThat(resultado).isPresent();
        verify(gateway).buscarPorSku(skuComEspacos);
    }

    @Test
    void deveConsultarComSkuComCaracteresEspeciais() {
        // Given
        String skuEspecial = "SKU-@123#456$";
        Estoque estoqueEspecial = Estoque.builder()
                .id(2L)
                .sku(skuEspecial)
                .quantidadeDisponivel(10)
                .build();

        when(gateway.buscarPorSku(skuEspecial)).thenReturn(Optional.of(estoqueEspecial));

        // When
        Optional<Estoque> resultado = service.execute(skuEspecial);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getSku()).isEqualTo(skuEspecial);
        verify(gateway).buscarPorSku(skuEspecial);
    }

    @Test
    void deveConsultarComSkuLongo() {
        // Given
        String skuLongo = "SKU-" + "A".repeat(100);
        when(gateway.buscarPorSku(skuLongo)).thenReturn(Optional.empty());

        // When
        Optional<Estoque> resultado = service.execute(skuLongo);

        // Then
        assertThat(resultado).isEmpty();
        verify(gateway).buscarPorSku(skuLongo);
    }

    @Test
    void deveRetornarEstoqueComQuantidadeZero() {
        // Given
        Estoque estoqueZero = Estoque.builder()
                .id(1L)
                .sku(skuPadrao)
                .quantidadeDisponivel(0)
                .build();

        when(gateway.buscarPorSku(skuPadrao)).thenReturn(Optional.of(estoqueZero));

        // When
        Optional<Estoque> resultado = service.execute(skuPadrao);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getQuantidadeDisponivel()).isEqualTo(0);
    }

    @Test
    void deveRetornarEstoqueComQuantidadeAlta() {
        // Given
        Estoque estoqueAlto = Estoque.builder()
                .id(1L)
                .sku(skuPadrao)
                .quantidadeDisponivel(999999)
                .build();

        when(gateway.buscarPorSku(skuPadrao)).thenReturn(Optional.of(estoqueAlto));

        // When
        Optional<Estoque> resultado = service.execute(skuPadrao);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getQuantidadeDisponivel()).isEqualTo(999999);
    }

    @Test
    void deveConsultarDiferentesSkusCorretamente() {
        // Given
        String[] skus = {"SKU-001", "SKU-002", "SKU-003"};

        for (String sku : skus) {
            when(gateway.buscarPorSku(sku)).thenReturn(Optional.empty());

            // When
            Optional<Estoque> resultado = service.execute(sku);

            // Then
            assertThat(resultado).isEmpty();
        }

        verify(gateway, times(skus.length)).buscarPorSku(anyString());
    }

    @Test
    void deveLancarExcecaoEspecificaDoGateway() {
        // Given
        IllegalArgumentException excecaoEspecifica =
                new IllegalArgumentException("SKU inválido");
        when(gateway.buscarPorSku(skuPadrao)).thenThrow(excecaoEspecifica);

        // When & Then
        assertThatThrownBy(() -> service.execute(skuPadrao))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("SKU inválido");
    }

    @Test
    void deveRetornarEstoqueComIdNulo() {
        // Given
        Estoque estoqueIdNulo = Estoque.builder()
                .id(null)
                .sku(skuPadrao)
                .quantidadeDisponivel(10)
                .build();

        when(gateway.buscarPorSku(skuPadrao)).thenReturn(Optional.of(estoqueIdNulo));

        // When
        Optional<Estoque> resultado = service.execute(skuPadrao);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isNull();
        assertThat(resultado.get().getSku()).isEqualTo(skuPadrao);
    }
}
