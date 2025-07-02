package com.fiap.cliente.controller;

import com.fiap.cliente.dto.EstoqueDTO;
import com.fiap.cliente.mapper.EstoqueMapper;
import com.fiap.cliente.usecase.AtualizarEstoqueUseCase;
import com.fiap.cliente.usecase.BaixarEstoqueUseCase;
import com.fiap.cliente.usecase.ConsultarEstoqueUseCase;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final ConsultarEstoqueUseCase consultarUseCase;
    private final AtualizarEstoqueUseCase atualizarUseCase;
    private final BaixarEstoqueUseCase baixarUseCase;
    private final EstoqueMapper mapper;

    @GetMapping("/{sku}")
    public EstoqueDTO consultar(@PathVariable String sku) {
        return consultarUseCase.execute(sku)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
    }

    @PutMapping
    public EstoqueDTO atualizar(@RequestBody EstoqueDTO dto) {
        var estoque = mapper.toDomain(dto);
        return mapper.toDTO(atualizarUseCase.execute(estoque));
    }

    @PostMapping("/{sku}/baixa")
    public EstoqueDTO baixar(@PathVariable String sku, @RequestParam int quantidade) {
        return mapper.toDTO(baixarUseCase.execute(sku, quantidade));
    }
}
