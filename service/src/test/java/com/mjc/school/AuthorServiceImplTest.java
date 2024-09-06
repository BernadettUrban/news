package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;
import com.mjc.school.mappers.AuthorMapper;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
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

    @Mock
    private NewsRepository newsRepository;

    private Author author1;
    private Author author2;
    private List<Author> authors;

    private AuthorDTO authorDTO1;
    private AuthorDTO authorDTO2;
    private List<AuthorDTO> authorDTOs;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        author1 = new Author.Builder()
                .id(1L)
                .name("Author1")
                .build();

        author2 = new Author.Builder()
                .id(2L)
                .name("Author2")
                .build();

        authors = Arrays.asList(author1, author2);

        authorDTO1 = new AuthorDTO(1L, "Author1", 5L);
        authorDTO2 = new AuthorDTO(2L, "Author2", 10L);
        authorDTOs = Arrays.asList(authorDTO1, authorDTO2);
    }

    @Test
    void testListAllAuthors() {
        // Given
        Author author1 = new Author.Builder().id(1L).name("John Doe").build();
        Author author2 = new Author.Builder().id(2L).name("Jane Doe").build();
        AuthorDTO authorDTO1 = new AuthorDTO(1L, "John Doe", 0L);
        AuthorDTO authorDTO2 = new AuthorDTO(2L, "Jane Doe", 0L);
        Page<Author> authorPage = new PageImpl<>(List.of(author1, author2));
        Page<AuthorDTO> expectedPage = new PageImpl<>(List.of(authorDTO1, authorDTO2));

        // When
        when(authorRepository.findAll(PageRequest.of(0, 10))).thenReturn(authorPage);
        when(authorMapper.entityToDTO(author1)).thenReturn(authorDTO1);
        when(authorMapper.entityToDTO(author2)).thenReturn(authorDTO2);

        // Then
        Page<AuthorDTO> result = authorService.listAllAuthors(0, 10);

        // Verify
        assertEquals(expectedPage, result);
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
        Author author = new Author.Builder()
                .name("John Doe")
                .build();

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorService.saveAuthor(author);

        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testSearchAuthorsByName() {
        String name = "John";
        Author author = new Author.Builder()
                .name("John Doe")
                .build();
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
    void testGetAuthorsByName() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Author> authorPage = new PageImpl<>(authors, pageable, authors.size());
        when(authorRepository.findByNameContaining("Author", pageable)).thenReturn(authorPage);
        when(authorMapper.entityToDTO(author1)).thenReturn(authorDTO1);
        when(authorMapper.entityToDTO(author2)).thenReturn(authorDTO2);

        Page<AuthorDTO> result = authorService.getAuthorsByName("Author", pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals(authorDTO1, result.getContent().get(0));
        assertEquals(authorDTO2, result.getContent().get(1));

        verify(authorRepository, times(1)).findByNameContaining("Author", pageable);
        verify(authorMapper, times(1)).entityToDTO(author1);
        verify(authorMapper, times(1)).entityToDTO(author2);
    }

    @Test
    void testGetAuthorByNewsId() {
        Long newsId = 1L;
        Author author = new Author.Builder().id(1L).name("Author1").build();
        when(authorRepository.findByNewsId(newsId)).thenReturn(author);
        when(newsRepository.countByAuthorId(author.getId())).thenReturn(5L);
        when(authorMapper.entityToDTO(author)).thenReturn(new AuthorDTO(1L, "Author1", 0L));

        AuthorDTO result = authorService.getAuthorByNewsId(newsId);

        assertEquals(5L, result.newsCount());
        verify(authorRepository, times(1)).findByNewsId(newsId);
        verify(newsRepository, times(1)).countByAuthorId(author.getId());
    }

    @Test
    void testUpdateAuthor() {
        Long authorId = 1L;
        CreateAuthorDTO createAuthorDTO = new CreateAuthorDTO("Updated Name");

        Author author = new Author.Builder()
                .id(authorId)
                .name("Old Name")
                .build();

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
        // Given
        Author author1 = new Author.Builder()
                .id(1L)
                .name("John Doe")
                .news(List.of(new News(), new News())) // 2 news items
                .build();
        Author author2 = new Author.Builder()
                .id(2L)
                .name("Jane Doe")
                .news(Collections.emptyList()) // 0 news items
                .build();
        Page<Author> authorPage = new PageImpl<>(List.of(author1, author2));
        Pageable pageable = PageRequest.of(0, 10);

        // When
        when(authorRepository.findAll(pageable)).thenReturn(authorPage);

        // Then
        Page<AuthorDTO> result = authorService.getAuthorsWithNewsCount(pageable);

        // Verify
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(2L, result.getContent().get(0).newsCount()); // Check news count for author1
        assertEquals(0L, result.getContent().get(1).newsCount()); // Check news count for author2

        verify(authorRepository, times(1)).findAll(pageable);
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
        when(authorRepository.findById(nonExistentId)).thenReturn(Optional.empty());

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
            authorService.getAuthorsWithNewsCount(PageRequest.of(-1, 10));
        });

        assertEquals("Page index must not be less than zero", exception.getMessage());
    }

    @Test
    void testGetAuthorsWithNewsCountWithNegativePageSizeShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.getAuthorsWithNewsCount(PageRequest.of(0, -10));
        });

        assertEquals("Page size must not be less than one", exception.getMessage());
    }
}
