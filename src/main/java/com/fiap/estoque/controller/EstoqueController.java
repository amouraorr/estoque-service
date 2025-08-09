package com.fiap.estoque.controller;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.dto.request.EstoqueRequestDTO;
import com.fiap.estoque.dto.response.EstoqueResponseDTO;
import com.fiap.estoque.mapper.EstoqueMapper;
import com.fiap.estoque.usecase.service.AtualizarEstoqueServiceUseCase;
import com.fiap.estoque.usecase.service.BaixarEstoqueServiceUseCase;
import com.fiap.estoque.usecase.service.ConsultarEstoqueServiceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final ConsultarEstoqueServiceUseCase consultarUseCase;
    private final AtualizarEstoqueServiceUseCase atualizarUseCase;
    private final BaixarEstoqueServiceUseCase baixarUseCase;
    private final EstoqueMapper mapper;

    @GetMapping("/{sku}")
    public EstoqueResponseDTO consultar(@PathVariable String sku) {
        log.info("Consultando estoque para SKU: {}", sku);
        Optional<Estoque> estoqueOpt = consultarUseCase.execute(sku);
        Estoque estoque = estoqueOpt.orElseThrow(() -> {
            log.warn("Estoque não encontrado para SKU: {}", sku);
            return new RuntimeException("Estoque não encontrado para SKU: " + sku);
        });
        log.info("Estoque encontrado para SKU: {}, quantidade: {}", sku, estoque.getQuantidadeDisponivel());
        return mapper.toResponseDTO(estoque);
    }

    @PutMapping
    public EstoqueResponseDTO atualizar(@RequestBody EstoqueRequestDTO dto) {
        log.info("Atualizando estoque para SKU: {}", dto.getSku());
        var estoque = mapper.toDomain(dto);
        Estoque atualizado = atualizarUseCase.execute(estoque);
        log.info("Estoque atualizado para SKU: {}, nova quantidade: {}", atualizado.getSku(), atualizado.getQuantidadeDisponivel());
        return mapper.toResponseDTO(atualizado);
    }

    @PostMapping("/{sku}/baixa")
    public EstoqueResponseDTO baixar(@PathVariable String sku, @RequestParam int quantidade) {
        log.info("Baixando estoque para SKU: {}, quantidade: {}", sku, quantidade);
        Estoque atualizado = baixarUseCase.execute(sku, quantidade);
        log.info("Estoque após baixa para SKU: {}, quantidade disponível: {}", sku, atualizado.getQuantidadeDisponivel());
        return mapper.toResponseDTO(atualizado);
    }
}