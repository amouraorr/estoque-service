package com.fiap.cliente.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    private Long id;
    private String sku;
    private Integer quantidadeDisponivel;
}
