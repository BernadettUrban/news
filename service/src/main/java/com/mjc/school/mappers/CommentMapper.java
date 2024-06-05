package com.mjc.school.mappers;

import com.mjc.school.domain.Comment;
import com.mjc.school.dtos.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment dtoToEntity(CommentDTO dto);

    CommentDTO entityToDTO(Comment comment);


}
