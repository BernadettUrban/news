package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    List<Author> listAllAuthors();

    void deleteAuthorById(Long id);

    Optional<Author> getAuthorById(Long id);

    void saveAuthor(Author author);

    List<Author> searchAuthorsByName(String name);

    List<Author> getAuthorsOrderedByNewsCount();

    List<Tag> listAllTags();

    void deleteTagById(Long id);

    Optional<Tag> getTagById(Long id);

    void saveTag(Tag tag);

    List<Tag> searchTagsByName(String name);
}
