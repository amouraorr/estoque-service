package com.fiap.estoque.controller;

import com.fiap.estoque.dto.request.EstoqueRequestDTO;
import com.fiap.estoque.dto.response.EstoqueResponseDTO;
import com.fiap.estoque.mapper.EstoqueMapper;
import com.fiap.estoque.usecase.service.AtualizarEstoqueServiceUseCase;
import com.fiap.estoque.usecase.service.BaixarEstoqueServiceUseCase;
import com.fiap.estoque.usecase.service.ConsultarEstoqueServiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final ConsultarEstoqueServiceUseCase consultarUseCase;
    private final AtualizarEstoqueServiceUseCase atualizarUseCase;
    private final BaixarEstoqueServiceUseCase baixarUseCase;
    private final EstoqueMapper mapper;

    // Endpoint para consultar estoque por SKU
    @GetMapping("/{sku}")
    public EstoqueResponseDTO consultar(@PathVariable String sku) {
        return consultarUseCase.execute(sku)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
    }

    // Endpoint para atualizar estoque
    @PutMapping
    public EstoqueResponseDTO atualizar(@RequestBody EstoqueRequestDTO dto) {
        var estoque = mapper.toDomain(dto);
        return mapper.toResponseDTO(atualizarUseCase.execute(estoque));
    }

    // Endpoint para baixar (reduzir) estoque de um SKU
    @PostMapping("/{sku}/baixa")
    public EstoqueResponseDTO baixar(@PathVariable String sku, @RequestParam int quantidade) {
        return mapper.toResponseDTO(baixarUseCase.execute(sku, quantidade));
    }

    // Para futura integração com Kafka:
    // @KafkaListener(topics = "nome-do-topico", groupId = "estoque-service")
    // public void listen(String message) {
    //     // Processar mensagem recebida do Kafka
    // }
}