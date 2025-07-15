package com.fiap.estoque.mapper;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.dto.request.EstoqueRequestDTO;
import com.fiap.estoque.dto.response.EstoqueResponseDTO;
import com.fiap.estoque.entity.EstoqueEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-12T12:10:55-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Oracle Corporation)"
)
@Component
public class EstoqueMapperImpl implements EstoqueMapper {

    @Override
    public Estoque toDomain(EstoqueEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Estoque.EstoqueBuilder estoque = Estoque.builder();

        estoque.id( entity.getId() );
        estoque.sku( entity.getSku() );
        estoque.quantidadeDisponivel( entity.getQuantidadeDisponivel() );

        return estoque.build();
    }

    @Override
    public EstoqueEntity toEntity(Estoque domain) {
        if ( domain == null ) {
            return null;
        }

        EstoqueEntity.EstoqueEntityBuilder estoqueEntity = EstoqueEntity.builder();

        estoqueEntity.id( domain.getId() );
        estoqueEntity.sku( domain.getSku() );
        estoqueEntity.quantidadeDisponivel( domain.getQuantidadeDisponivel() );

        return estoqueEntity.build();
    }

    @Override
    public EstoqueResponseDTO toResponseDTO(Estoque domain) {
        if ( domain == null ) {
            return null;
        }

        EstoqueResponseDTO.EstoqueResponseDTOBuilder estoqueResponseDTO = EstoqueResponseDTO.builder();

        estoqueResponseDTO.id( domain.getId() );
        estoqueResponseDTO.sku( domain.getSku() );
        estoqueResponseDTO.quantidadeDisponivel( domain.getQuantidadeDisponivel() );

        return estoqueResponseDTO.build();
    }

    @Override
    public Estoque toDomain(EstoqueRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Estoque.EstoqueBuilder estoque = Estoque.builder();

        estoque.id( dto.getId() );
        estoque.sku( dto.getSku() );
        estoque.quantidadeDisponivel( dto.getQuantidadeDisponivel() );

        return estoque.build();
    }
}
