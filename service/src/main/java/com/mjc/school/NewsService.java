package com.mjc.school;

import com.mjc.school.domain.Author;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    List<Author> listAllAuthors();

    void deleteAuthorById(Long id);

    Optional<Author> getAuthorById(Long id);

    void saveAuthor(Author author);
    List<Author> searchAuthorsByName(String name);
}
