package com.mjc.school.mappers;

import com.mjc.school.domain.News;
import com.mjc.school.dtos.NewsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    News dtoToEntity(NewsDTO dto);

    NewsDTO entityToDTO(News news);

}
