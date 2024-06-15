package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;
import com.mjc.school.mappers.AuthorMapper;
import com.mjc.school.projection.AuthorNewsCountProjection;
import com.mjc.school.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorService = new AuthorServiceImpl(authorRepository, authorMapper);
    }

    @Test
    void testListAllAuthors() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Author1");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Author2");

        List<Author> authors = Arrays.asList(author1, author2);

        AuthorDTO authorDTO1 = new AuthorDTO(1L, "Author1", 5L);
        AuthorDTO authorDTO2 = new AuthorDTO(2L, "Author2", 10L);
        List<AuthorDTO> authorDTOs = Arrays.asList(authorDTO1, authorDTO2);

        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.entityToDTO(author1)).thenReturn(authorDTO1);
        when(authorMapper.entityToDTO(author2)).thenReturn(authorDTO2);

        List<AuthorDTO> result = authorService.listAllAuthors();

        assertEquals(authorDTOs, result);
        verify(authorRepository, times(1)).findAll();
    }
    @Test
    void testDeleteAuthorById() {
        Long authorId = 1L;

        authorService.deleteAuthorById(authorId);

        verify(authorRepository, times(1)).deleteById(authorId);
    }
    @Test
    void testGetAuthorById() {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        author.setName("John Doe");

        AuthorDTO authorDTO = new AuthorDTO(authorId, "John Doe", 5L);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorMapper.entityToDTO(author)).thenReturn(authorDTO);

        AuthorDTO result = authorService.getAuthorById(authorId);

        assertEquals(authorDTO, result);
        verify(authorRepository, times(1)).findById(authorId);
    }
    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setName("John Doe");

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorService.saveAuthor(author);

        verify(authorRepository, times(1)).save(author);
    }
    @Test
    void testSearchAuthorsByName() {
        String name = "John";
        Author author = new Author();
        author.setName("John Doe");
        List<Author> authors = Arrays.asList(author);

        AuthorDTO authorDTO = new AuthorDTO(1L, "John Doe", 5L);
        List<AuthorDTO> authorDTOs = Arrays.asList(authorDTO);

        when(authorRepository.findAuthorsByNameOrderedByNewsCount(name)).thenReturn(authors);
        when(authorMapper.entityToDTO(author)).thenReturn(authorDTO);

        List<AuthorDTO> result = authorService.searchAuthorsByName(name);

        assertEquals(authorDTOs, result);
        verify(authorRepository, times(1)).findAuthorsByNameOrderedByNewsCount(name);
    }
    @Test
    void testGetAuthorByNewsId() {
        Long newsId = 1L;
        Author author = new Author();
        author.setId(1L);
        author.setName("John Doe");

        AuthorDTO authorDTO = new AuthorDTO(1L, "John Doe", 5L);

        when(authorRepository.findByNewsId(newsId)).thenReturn(author);
        when(authorMapper.entityToDTO(author)).thenReturn(authorDTO);

        AuthorDTO result = authorService.getAuthorByNewsId(newsId);

        assertEquals(authorDTO, result);
        verify(authorRepository, times(1)).findByNewsId(newsId);
    }

    @Test
    void testUpdateAuthor() {
        Long authorId = 1L;
        CreateAuthorDTO createAuthorDTO = new CreateAuthorDTO("Updated Name");

        Author author = new Author();
        author.setId(authorId);
        author.setName("Old Name");

        AuthorDTO authorDTO = new AuthorDTO(authorId, "Updated Name", 5L);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(authorMapper.entityToDTO(author)).thenReturn(authorDTO);

        AuthorDTO result = authorService.updateAuthor(authorId, createAuthorDTO);

        assertEquals(authorDTO, result);
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).save(author);
    }
    @Test
    void testGetAuthorsOrderedByNewsCount() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Author1");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Author2");

        List<Author> authors = Arrays.asList(author1, author2);

        AuthorDTO authorDTO1 = new AuthorDTO(1L, "Author1", 5L);
        AuthorDTO authorDTO2 = new AuthorDTO(2L, "Author2", 10L);
        List<AuthorDTO> authorDTOs = Arrays.asList(authorDTO1, authorDTO2);

        when(authorRepository.findAuthorsOrderedByNewsCount()).thenReturn(authors);
        when(authorMapper.entityToDTO(author1)).thenReturn(authorDTO1);
        when(authorMapper.entityToDTO(author2)).thenReturn(authorDTO2);

        List<AuthorDTO> result = authorService.getAuthorsOrderedByNewsCount();

        assertEquals(authorDTOs, result);
        verify(authorRepository, times(1)).findAuthorsOrderedByNewsCount();
    }

    @Test
    void testGetAuthorsWithNewsCount() {
        AuthorNewsCountProjection projection1 = new AuthorNewsCountProjection() {

            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "Author1";
            }

            @Override
            public Long getNewsCount() {
                return 5L;
            }
        };

        AuthorNewsCountProjection projection2 = new AuthorNewsCountProjection() {
            @Override
            public Long getId() {
                return 2L;
            }

            @Override
            public String getName() {
                return "Author2";
            }

            @Override
            public Long getNewsCount() {
                return 10L;
            }
        };

        List<AuthorNewsCountProjection> projections = Arrays.asList(projection1, projection2);

        AuthorDTO authorDTO1 = new AuthorDTO(1L, "Author1", 5L);
        AuthorDTO authorDTO2 = new AuthorDTO(2L, "Author2", 10L);
        List<AuthorDTO> authorDTOs = Arrays.asList(authorDTO1, authorDTO2);

        Page<AuthorNewsCountProjection> projectionPage = new PageImpl<>(projections);
        Page<AuthorDTO> authorDTOPage = new PageImpl<>(authorDTOs);

        Pageable pageable = PageRequest.of(0, 10);

        when(authorRepository.findAuthorsWithNewsCount(pageable)).thenReturn(projectionPage);
        when(authorMapper.toAuthorDTO(projection1)).thenReturn(authorDTO1);
        when(authorMapper.toAuthorDTO(projection2)).thenReturn(authorDTO2);

        Page<AuthorDTO> result = authorService.getAuthorsWithNewsCount(pageable);

        assertEquals(authorDTOPage, result);
        verify(authorRepository, times(1)).findAuthorsWithNewsCount(pageable);
    }

}