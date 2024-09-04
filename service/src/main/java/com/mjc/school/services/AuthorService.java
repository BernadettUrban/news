package com.mjc.school.services;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    //List<AuthorDTO> listAllAuthors();
    Page<AuthorDTO> listAllAuthors(int page,int size);

    AuthorDTO createAuthor(CreateAuthorDTO createAuthorDTO);

    void deleteAuthorById(Long id);

    AuthorDTO getAuthorById(Long id);

    AuthorDTO saveAuthor(Author author);

    Author convertDtoToAuthor(AuthorDTO authorDTO);

    List<AuthorDTO> searchAuthorsByName(String name);

    AuthorDTO getAuthorByNewsId(Long newsId);

    AuthorDTO updateAuthor(Long authorId, CreateAuthorDTO createAuthorDTO);

    List<AuthorDTO> getAuthorsOrderedByNewsCount();

    Page<AuthorDTO> getAuthorsWithNewsCount(Pageable pageable);
    Page<AuthorDTO> getAuthorsByName(String name, Pageable pageable);
}
