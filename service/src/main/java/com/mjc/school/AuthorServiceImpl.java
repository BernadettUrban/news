package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.mappers.AuthorMapper;
import com.mjc.school.projection.AuthorNewsCountProjection;
import com.mjc.school.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public AuthorDTO createAuthor(CreateAuthorDTO createAuthorDTO) {
        Author author = new Author();
        author.setName(createAuthorDTO.name());
        authorRepository.save(author);
        return authorMapper.entityToDTO(author);
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
    public AuthorDTO updateAuthor(Long authorId, CreateAuthorDTO createAuthorDTO) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new CustomException("Comment not found with id: " + authorId));
        ;
        author.setName(createAuthorDTO.name());
        authorRepository.save(author);
        return authorMapper.entityToDTO(author);

    }

    @Override
    public List<AuthorDTO> getAuthorsOrderedByNewsCount() {

        return authorRepository.findAuthorsOrderedByNewsCount()
                .stream()
                .map(a -> authorMapper.entityToDTO(a))
                .collect(Collectors.toList());
    }

    public Page<AuthorDTO> getAuthorsWithNewsCount(Pageable pageable) {
        Page<AuthorNewsCountProjection> results = authorRepository.findAuthorsWithNewsCount(pageable);
        return results.map(authorMapper::toAuthorDTO);
    }

}
