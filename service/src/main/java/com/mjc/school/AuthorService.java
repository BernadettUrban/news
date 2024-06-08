package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> listAllAuthors();

    void deleteAuthorById(Long id);

    AuthorDTO getAuthorById(Long id);

    void saveAuthor(Author author);

    Author convertDtoToAuthor(AuthorDTO authorDTO);

    List<AuthorDTO> searchAuthorsByName(String name);

    AuthorDTO getAuthorByNewsId(Long newsId);

    List<AuthorDTO> getAuthorsOrderedByNewsCount();
}
