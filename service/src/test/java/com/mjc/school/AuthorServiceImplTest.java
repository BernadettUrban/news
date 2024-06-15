package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;
import com.mjc.school.mappers.AuthorMapper;
import com.mjc.school.projection.AuthorNewsCountProjection;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.services.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author1;
    private Author author2;
    private List<Author> authors;

    private AuthorDTO authorDTO1;
    private AuthorDTO authorDTO2;
    private List<AuthorDTO> authorDTOs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author1 = new Author();
        author1.setId(1L);
        author1.setName("Author1");

        author2 = new Author();
        author2.setId(2L);
        author2.setName("Author2");

        authors = Arrays.asList(author1, author2);

        authorDTO1 = new AuthorDTO(1L, "Author1", 5L);
        authorDTO2 = new AuthorDTO(2L, "Author2", 10L);
        authorDTOs = Arrays.asList(authorDTO1, authorDTO2);
    }

    @Test
    void testListAllAuthors() {

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
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author1));
        authorService.deleteAuthorById(authorId);

        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    void testGetAuthorById() {
        Long authorId = 1L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author1));
        when(authorMapper.entityToDTO(author1)).thenReturn(authorDTO1);

        AuthorDTO result = authorService.getAuthorById(authorId);

        assertEquals(authorDTO1, result);
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

        when(authorRepository.findByNewsId(newsId)).thenReturn(author1);
        when(authorMapper.entityToDTO(author1)).thenReturn(authorDTO1);

        AuthorDTO result = authorService.getAuthorByNewsId(newsId);

        assertEquals(authorDTO1, result);
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

    @Test
    void testCreateAuthorWithNullValueNameInCreateAuthorDTOShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.createAuthor(new CreateAuthorDTO(null));
        });

        assertEquals("CreateAuthorDTO cannot be null and must have a name", exception.getMessage());
    }

    @Test
    void testDeleteAuthorByIdWithNullIdShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.deleteAuthorById(null);
        });

        assertEquals("Author ID cannot be null", exception.getMessage());
    }

    @Test
    void testGetAuthorByIdWithNullValueIdShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.getAuthorById(null);
        });

        assertEquals("Author ID cannot be null", exception.getMessage());
    }

    @Test
    void testGetAuthorByIdWithNonexistentIdShouldThrowIllegalArgumentException() {
        Long nonExistentId = 999L;
        when(authorRepository.findById(nonExistentId)).thenReturn(java.util.Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            authorService.getAuthorById(nonExistentId);
        });

        assertEquals("Author not found with id: " + nonExistentId, exception.getMessage());
    }

    @Test
    void testSaveAuthorWithNullValueAuthorShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.saveAuthor(null);
        });

        assertEquals("Author entity cannot be null", exception.getMessage());
    }

    @Test
    void testConvertDtoToAuthorWithNullValueAuthorDTOShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.convertDtoToAuthor(null);
        });

        assertEquals("AuthorDTO cannot be null", exception.getMessage());
    }

    @Test
    void testSearchAuthorsByNameWithNullValueNameShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.searchAuthorsByName(null);
        });

        assertEquals("Author name cannot be null", exception.getMessage());
    }

    @Test
    void testGetAuthorByNewsIdWithNullValueNewsIdShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.getAuthorByNewsId(null);
        });

        assertEquals("News ID cannot be null", exception.getMessage());
    }

    @Test
    void testUpdateAuthorWithNullValueAuthorIdShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.updateAuthor(null, new CreateAuthorDTO("John Doe"));
        });

        assertEquals("Author ID cannot be null", exception.getMessage());
    }

    @Test
    void testUpdateAuthorWithNullValueCreateAuthorDTOShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.updateAuthor(1L, null);
        });

        assertEquals("CreateAuthorDTO cannot be null and must have a name", exception.getMessage());
    }

    @Test
    void testGetAuthorsWithNewsCountWithNullValuePageableShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.getAuthorsWithNewsCount(null);
        });

        assertEquals("Pageable object cannot be null", exception.getMessage());
    }

    @Test
    void testGetAuthorsWithNewsCountWithNegativePageIndexShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.getAuthorsWithNewsCount(Pageable.ofSize(10).withPage(-1));
        });

        assertEquals("Page index must not be less than zero", exception.getMessage());
    }

    @Test
    void testGetAuthorsWithNewsCountWithNegativePageSizeShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.getAuthorsWithNewsCount(Pageable.ofSize(-10).withPage(0));
        });

        assertEquals("Page size must not be less than one", exception.getMessage());
    }

}