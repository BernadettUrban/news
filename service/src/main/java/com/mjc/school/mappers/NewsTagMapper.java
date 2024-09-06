package com.mjc.school.mappers;

import com.mjc.school.domain.NewsTag;
import com.mjc.school.dtos.NewsTagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface NewsTagMapper {
    NewsTagMapper INSTANCE = Mappers.getMapper(NewsTagMapper.class);

    NewsTag dtoToEntity(NewsTagDTO dto);

    NewsTagDTO entityToDTO(NewsTag newsTag);
}
