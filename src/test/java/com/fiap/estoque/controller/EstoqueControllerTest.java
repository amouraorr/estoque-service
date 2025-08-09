package com.fiap.estoque.controller;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.dto.request.EstoqueRequestDTO;
import com.fiap.estoque.dto.response.EstoqueResponseDTO;
import com.fiap.estoque.mapper.EstoqueMapper;
import com.fiap.estoque.usecase.service.AtualizarEstoqueServiceUseCase;
import com.fiap.estoque.usecase.service.BaixarEstoqueServiceUseCase;
import com.fiap.estoque.usecase.service.ConsultarEstoqueServiceUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueControllerTest {

    @Mock
    private ConsultarEstoqueServiceUseCase consultarUseCase;

    @Mock
    private AtualizarEstoqueServiceUseCase atualizarUseCase;

    @Mock
    private BaixarEstoqueServiceUseCase baixarUseCase;

    @Mock
    private EstoqueMapper mapper;

    @InjectMocks
    private EstoqueController estoqueController;

    @Test
    void deveConsultarEstoqueComSucesso() {
        // Arrange
        String sku = "SKU123";
        Estoque estoque = criarEstoque(sku, 100);
        EstoqueResponseDTO responseDTO = criarEstoqueResponseDTO(sku, 100);

        when(consultarUseCase.execute(sku)).thenReturn(Optional.of(estoque));
        when(mapper.toResponseDTO(estoque)).thenReturn(responseDTO);

        // Act
        EstoqueResponseDTO resultado = estoqueController.consultar(sku);

        // Assert
        assertNotNull(resultado);
        assertEquals(sku, resultado.getSku());
        assertEquals(100, resultado.getQuantidadeDisponivel());

        verify(consultarUseCase, times(1)).execute(sku);
        verify(mapper, times(1)).toResponseDTO(estoque);
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueNaoEncontrado() {
        // Arrange
        String sku = "SKU_INEXISTENTE";

        when(consultarUseCase.execute(sku)).thenReturn(Optional.empty());

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> estoqueController.consultar(sku));

        assertEquals("Estoque não encontrado para SKU: " + sku, exception.getMessage());

        verify(consultarUseCase, times(1)).execute(sku);
        verify(mapper, never()).toResponseDTO(any());
    }

    @Test
    void deveAtualizarEstoqueComSucesso() {
        // Arrange
        String sku = "SKU123";
        EstoqueRequestDTO requestDTO = criarEstoqueRequestDTO(sku, 150);
        Estoque estoque = criarEstoque(sku, 150);
        Estoque estoqueAtualizado = criarEstoque(sku, 150);
        EstoqueResponseDTO responseDTO = criarEstoqueResponseDTO(sku, 150);

        when(mapper.toDomain(requestDTO)).thenReturn(estoque);
        when(atualizarUseCase.execute(estoque)).thenReturn(estoqueAtualizado);
        when(mapper.toResponseDTO(estoqueAtualizado)).thenReturn(responseDTO);

        // Act
        EstoqueResponseDTO resultado = estoqueController.atualizar(requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(sku, resultado.getSku());
        assertEquals(150, resultado.getQuantidadeDisponivel());

        verify(mapper, times(1)).toDomain(requestDTO);
        verify(atualizarUseCase, times(1)).execute(estoque);
        verify(mapper, times(1)).toResponseDTO(estoqueAtualizado);
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        // Arrange
        String sku = "SKU123";
        int quantidadeBaixa = 20;
        int quantidadeRestante = 80;
        Estoque estoqueAtualizado = criarEstoque(sku, quantidadeRestante);
        EstoqueResponseDTO responseDTO = criarEstoqueResponseDTO(sku, quantidadeRestante);

        when(baixarUseCase.execute(sku, quantidadeBaixa)).thenReturn(estoqueAtualizado);
        when(mapper.toResponseDTO(estoqueAtualizado)).thenReturn(responseDTO);

        // Act
        EstoqueResponseDTO resultado = estoqueController.baixar(sku, quantidadeBaixa);

        // Assert
        assertNotNull(resultado);
        assertEquals(sku, resultado.getSku());
        assertEquals(quantidadeRestante, resultado.getQuantidadeDisponivel());

        verify(baixarUseCase, times(1)).execute(sku, quantidadeBaixa);
        verify(mapper, times(1)).toResponseDTO(estoqueAtualizado);
    }

    @Test
    void deveConsultarEstoqueComSkuVazio() {
        // Arrange
        String sku = "";

        when(consultarUseCase.execute(sku)).thenReturn(Optional.empty());

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> estoqueController.consultar(sku));

        assertEquals("Estoque não encontrado para SKU: " + sku, exception.getMessage());

        verify(consultarUseCase, times(1)).execute(sku);
        verify(mapper, never()).toResponseDTO(any());
    }

    @Test
    void deveAtualizarEstoqueComQuantidadeZero() {
        // Arrange
        String sku = "SKU123";
        EstoqueRequestDTO requestDTO = criarEstoqueRequestDTO(sku, 0);
        Estoque estoque = criarEstoque(sku, 0);
        Estoque estoqueAtualizado = criarEstoque(sku, 0);
        EstoqueResponseDTO responseDTO = criarEstoqueResponseDTO(sku, 0);

        when(mapper.toDomain(requestDTO)).thenReturn(estoque);
        when(atualizarUseCase.execute(estoque)).thenReturn(estoqueAtualizado);
        when(mapper.toResponseDTO(estoqueAtualizado)).thenReturn(responseDTO);

        // Act
        EstoqueResponseDTO resultado = estoqueController.atualizar(requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(sku, resultado.getSku());
        assertEquals(0, resultado.getQuantidadeDisponivel());

        verify(mapper, times(1)).toDomain(requestDTO);
        verify(atualizarUseCase, times(1)).execute(estoque);
        verify(mapper, times(1)).toResponseDTO(estoqueAtualizado);
    }

    @Test
    void deveBaixarEstoqueComQuantidadeMaxima() {
        // Arrange
        String sku = "SKU123";
        int quantidadeBaixa = 100;
        int quantidadeRestante = 0;
        Estoque estoqueAtualizado = criarEstoque(sku, quantidadeRestante);
        EstoqueResponseDTO responseDTO = criarEstoqueResponseDTO(sku, quantidadeRestante);

        when(baixarUseCase.execute(sku, quantidadeBaixa)).thenReturn(estoqueAtualizado);
        when(mapper.toResponseDTO(estoqueAtualizado)).thenReturn(responseDTO);

        // Act
        EstoqueResponseDTO resultado = estoqueController.baixar(sku, quantidadeBaixa);

        // Assert
        assertNotNull(resultado);
        assertEquals(sku, resultado.getSku());
        assertEquals(quantidadeRestante, resultado.getQuantidadeDisponivel());

        verify(baixarUseCase, times(1)).execute(sku, quantidadeBaixa);
        verify(mapper, times(1)).toResponseDTO(estoqueAtualizado);
    }

    // Métodos auxiliares para criação de objetos de teste
    private Estoque criarEstoque(String sku, int quantidade) {
        return Estoque.builder()
                .sku(sku)
                .quantidadeDisponivel(quantidade)
                .build();
    }

    private EstoqueRequestDTO criarEstoqueRequestDTO(String sku, int quantidade) {
        return EstoqueRequestDTO.builder()
                .sku(sku)
                .quantidadeDisponivel(quantidade)
                .build();
    }

    private EstoqueResponseDTO criarEstoqueResponseDTO(String sku, int quantidade) {
        return EstoqueResponseDTO.builder()
                .sku(sku)
                .quantidadeDisponivel(quantidade)
                .build();
    }
}