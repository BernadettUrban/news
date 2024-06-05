package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorDTO> listAllAuthors();

    void deleteAuthorById(Long id);

    Optional<Author> getAuthorById(Long id);

    void saveAuthor(Author author);

    List<Author> searchAuthorsByName(String name);

    List<Author> getAuthorsOrderedByNewsCount();
}
