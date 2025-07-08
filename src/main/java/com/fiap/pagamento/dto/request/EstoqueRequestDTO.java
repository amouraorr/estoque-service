package com.fiap.pagamento.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueRequestDTO {
    private Long id;
    private String sku;
    private Integer quantidadeDisponivel;
}
