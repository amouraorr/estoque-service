package com.fiap.estoque.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueResponseDTO {
    private Long id;
    private String sku;
    private Integer quantidadeDisponivel;
}
