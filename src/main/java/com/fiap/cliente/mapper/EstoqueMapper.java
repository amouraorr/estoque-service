package com.fiap.cliente.mapper;

import com.fiap.cliente.domain.Estoque;
import com.fiap.cliente.dto.EstoqueDTO;
import com.fiap.cliente.entity.EstoqueEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstoqueMapper {
    Estoque toDomain(EstoqueEntity entity);
    EstoqueEntity toEntity(Estoque domain);
    EstoqueDTO toDTO(Estoque domain);
    Estoque toDomain(EstoqueDTO dto);
}