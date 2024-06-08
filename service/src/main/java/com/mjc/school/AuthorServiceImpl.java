package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.mappers.AuthorMapper;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDTO> listAllAuthors() {
        return authorRepository.findAll().stream()
                .map(a -> authorMapper.entityToDTO(a))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        return authorMapper.entityToDTO(authorRepository.findById(id).get());
    }

    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public Author convertDtoToAuthor(AuthorDTO authorDTO) {
        return authorMapper.dtoToEntity(authorDTO);
    }


    @Override
    public List<AuthorDTO> searchAuthorsByName(String name) {
        return authorRepository.findAuthorsByNameOrderedByNewsCount(name)
                .stream()
                .map(a -> authorMapper.entityToDTO(a))
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorByNewsId(Long newsId) {
        Author author = authorRepository.findByNewsId(newsId);
        return authorMapper.entityToDTO(author);
    }

    @Override
    public List<AuthorDTO> getAuthorsOrderedByNewsCount() {

        return authorRepository.findAuthorsOrderedByNewsCount()
                .stream()
                .map(a -> authorMapper.entityToDTO(a))
                .collect(Collectors.toList());
    }

}
