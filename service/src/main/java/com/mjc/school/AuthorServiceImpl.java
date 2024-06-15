package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.exceptions.PaginationException;
import com.mjc.school.mappers.AuthorMapper;
import com.mjc.school.projection.AuthorNewsCountProjection;
import com.mjc.school.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
        if (createAuthorDTO == null || createAuthorDTO.name() == null) {
            throw new IllegalArgumentException("CreateAuthorDTO cannot be null and must have a name");
        }
        Author author = new Author();
        author.setName(createAuthorDTO.name());
        authorRepository.save(author);
        return authorMapper.entityToDTO(author);
    }

    @Override
    public void deleteAuthorById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Author ID cannot be null");
        }
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Author ID cannot be null");
        }
        return authorMapper.entityToDTO(authorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author not found with id: " + id)));
    }

    @Override
    public AuthorDTO saveAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Author entity cannot be null");
        }
        AuthorDTO authorDTO = authorMapper.entityToDTO(author);
        authorRepository.save(author);
        return authorDTO;
    }

    @Override
    public Author convertDtoToAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            throw new IllegalArgumentException("AuthorDTO cannot be null");
        }
        return authorMapper.dtoToEntity(authorDTO);
    }


    @Override
    public List<AuthorDTO> searchAuthorsByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Author name cannot be null");
        }
        return authorRepository.findAuthorsByNameOrderedByNewsCount(name)
                .stream()
                .map(a -> authorMapper.entityToDTO(a))
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorByNewsId(Long newsId) {
        if (newsId == null) {
            throw new IllegalArgumentException("News ID cannot be null");
        }
        Author author = authorRepository.findByNewsId(newsId);
        return authorMapper.entityToDTO(author);
    }

    @Override
    public AuthorDTO updateAuthor(Long authorId, CreateAuthorDTO createAuthorDTO) {
        if (authorId == null) {
            throw new IllegalArgumentException("Author ID cannot be null");
        }
        if (createAuthorDTO == null || createAuthorDTO.name() == null) {
            throw new IllegalArgumentException("CreateAuthorDTO cannot be null and must have a name");
        }
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Author not found with id: " + authorId));
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
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable object cannot be null");
        }
        if (pageable.getPageNumber() < 0) {
            throw new IllegalArgumentException("Page index must be not negative");
        }
        if (pageable.getPageSize() < 0) {
            throw new IllegalArgumentException("Size must be not negative");
        }
        Page<AuthorNewsCountProjection> results = authorRepository.findAuthorsWithNewsCount(pageable);
        return results.map(authorMapper::toAuthorDTO);
    }

}
