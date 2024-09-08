package com.mjc.school.services;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;
import com.mjc.school.mappers.AuthorMapper;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.AuthorRepositoryCustom;
import com.mjc.school.repository.NewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    //private final AuthorRepositoryCustom authorRepositoryCustom;
    private final AuthorMapper authorMapper;
    private final NewsRepository newsRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper, NewsRepository newsRepository) {
        this.authorRepository = authorRepository;
       // this.authorRepositoryCustom = authorRepositoryCustom;
        this.authorMapper = authorMapper;
        this.newsRepository = newsRepository;
    }


    @Override
    public Page<AuthorDTO> listAllAuthors(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Author> authorPage = authorRepository.findAll(pageRequest);

        return authorPage.map(authorMapper::entityToDTO);

    }

    @Transactional
    @Override
    public AuthorDTO createAuthor(CreateAuthorDTO createAuthorDTO) {
        if (createAuthorDTO == null || createAuthorDTO.name() == null) {
            throw new IllegalArgumentException("CreateAuthorDTO cannot be null and must have a name");
        }
        Author author = new Author.Builder()
                .name(createAuthorDTO.name())
                .build();
        authorRepository.save(author);
        return authorMapper.entityToDTO(author);
    }

    @Transactional
    @Override
    public void deleteAuthorById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Author ID cannot be null");
        }
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            for (News news : author.getNews()) {
                news.setAuthor(null);
            }
            authorRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Author with id " + id + " not found");
        }
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Author ID cannot be null");
        }
        return authorMapper.entityToDTO(authorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author not found with id: " + id)));
    }

    @Transactional
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


    /*@Override
    public List<AuthorDTO> searchAuthorsByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Author name cannot be null");
        }
        List<Author> authors = authorRepository.findAuthorsByNameOrderedByNewsCount(name);

        if (authors.isEmpty()) {
            // Handle the case when no authors are found, if necessary
            // For example, return an empty list or throw an exception
            return Collections.emptyList();
        }

        return authors.stream()
                .map(authorMapper::entityToDTO)
                .collect(Collectors.toList());
    }
*/
    @Override
    public AuthorDTO getAuthorByNewsId(Long newsId) {
        if (newsId == null) {
            throw new IllegalArgumentException("News ID cannot be null");
        }
        Author author = authorRepository.findByNewsId(newsId);
        long newsCount = newsRepository.countByAuthorId(author.getId());

        AuthorDTO authorDTO = authorMapper.entityToDTO(author);
        authorDTO = new AuthorDTO(authorDTO.id(), authorDTO.name(), newsCount);

        return authorDTO;
    }

    @Transactional
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
        author.setName(createAuthorDTO.name());
        authorRepository.save(author);
        return authorMapper.entityToDTO(author);

    }

    /*
    @Override
    public List<AuthorDTO> getAuthorsOrderedByNewsCount() {

        return authorRepository.findAuthorsOrderedByNewsCount()
                .stream()
                .map(a -> authorMapper.entityToDTO(a))
                .collect(Collectors.toList());
    }
*/
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

        Page<Object[]> results = authorRepository.findAllAuthors(pageable);

        List<AuthorDTO> authorDTOs = results.getContent().stream()
                .map(record -> new AuthorDTO(
                        (Long) record[0], // id
                        (String) record[1], // name
                        (Long) record[2] // newsCount
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(authorDTOs, pageable, results.getTotalElements());
    }

    @Override
    public Page<AuthorDTO> getAuthorsByName(String name, Pageable pageable) {

        if(name == null){
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable object cannot be null");
        }
        if (pageable.getPageNumber() < 0) {
            throw new IllegalArgumentException("Page index must be not negative");
        }
        if (pageable.getPageSize() < 0) {
            throw new IllegalArgumentException("Size must be not negative");
        }

        Page<Object[]> results = authorRepository.findAuthorsByName(name, pageable);

        List<AuthorDTO> authorDTOs = results.getContent().stream()
                .map(record -> new AuthorDTO(
                        (Long) record[0], // id
                        (String) record[1], // name
                        (Long) record[2] // newsCount
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(authorDTOs, pageable, results.getTotalElements());
    }

    @Override
    public void deleteAll() {
        authorRepository.deleteAll();
    }
}

