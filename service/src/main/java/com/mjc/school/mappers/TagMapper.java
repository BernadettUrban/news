package com.mjc.school.mappers;

import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.CreateTagDTO;
import com.mjc.school.dtos.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    Tag dtoToEntity(TagDTO dto);

    TagDTO entityToDTO(Tag tag);

    Tag createTagDTOToEntity(CreateTagDTO createTagDTO);
}
