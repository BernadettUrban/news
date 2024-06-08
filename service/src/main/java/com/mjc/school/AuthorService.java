package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> listAllAuthors();

    AuthorDTO createAuthor(CreateAuthorDTO createAuthorDTO);

    void deleteAuthorById(Long id);

    AuthorDTO getAuthorById(Long id);

    void saveAuthor(Author author);

    Author convertDtoToAuthor(AuthorDTO authorDTO);

    List<AuthorDTO> searchAuthorsByName(String name);

    AuthorDTO getAuthorByNewsId(Long newsId);

    AuthorDTO updateAuthor(Long authorId, CreateAuthorDTO createAuthorDTO);

    List<AuthorDTO> getAuthorsOrderedByNewsCount();
}
