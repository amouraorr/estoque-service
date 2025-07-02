package com.fiap.cliente.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueDTO {
    private Long id;
    private String sku;
    private Integer quantidadeDisponivel;
}
