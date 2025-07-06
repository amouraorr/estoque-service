package com.fiap.pagamento.mapper;

import com.fiap.pagamento.domain.Estoque;
import com.fiap.pagamento.dto.request.EstoqueRequestDTO;
import com.fiap.pagamento.dto.response.EstoqueResponseDTO;
import com.fiap.pagamento.entity.EstoqueEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstoqueMapper {
    Estoque toDomain(EstoqueEntity entity);
    EstoqueEntity toEntity(Estoque domain);
    EstoqueResponseDTO toResponseDTO(Estoque domain);
    Estoque toDomain(EstoqueRequestDTO dto);
}