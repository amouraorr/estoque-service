package com.fiap.estoque.mapper;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.dto.request.EstoqueRequestDTO;
import com.fiap.estoque.dto.response.EstoqueResponseDTO;
import com.fiap.estoque.entity.EstoqueEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstoqueMapper {
    Estoque toDomain(EstoqueEntity entity);
    EstoqueEntity toEntity(Estoque domain);
    EstoqueResponseDTO toResponseDTO(Estoque domain);
    Estoque toDomain(EstoqueRequestDTO dto);
}