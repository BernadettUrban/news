package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.mappers.AuthorMapper;
import com.mjc.school.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDTO> listAllAuthors() {
        return authorRepository.findAll().stream()
                .map(a-> authorMapper.entityToDTO(a))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public List<Author> searchAuthorsByName(String name) {
        return authorRepository.findAuthorsByNameOrderedByNewsCount(name);
    }

    @Override
    public List<Author> getAuthorsOrderedByNewsCount() {
        return authorRepository.findAuthorsOrderedByNewsCount();
    }

}
