package com.mjc.school.mappers;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author dtoToEntity(AuthorDTO dto);

    AuthorDTO entityToDTO(Author author);

    List<Author> authorDTOsToAuthors(List<AuthorDTO> authorDTOs);
    List<AuthorDTO> authorsToAuthorDTOs(List<Author> authors);
}
