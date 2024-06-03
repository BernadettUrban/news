package com.mjc.school.mappers;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO getAuthorDtoFromAuthor(Author author);

    List<AuthorDTO> getAuthorDTOsFromAuthors(List<Author> authors);
}
