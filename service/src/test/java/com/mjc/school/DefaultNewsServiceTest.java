package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.Tag;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DefaultNewsServiceTest {


    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAllAuthors() {
        Author author1 = new Author();
        author1.setName("Author1");
        Author author2 = new Author();
        author2.setName("Author2");
        List<Author> authors = Arrays.asList(author1, author2);

        when(authorRepository.findAll()).thenReturn(authors);

       // List<Author> result = newsService.listAllAuthors();

        //assertEquals(authors, result);
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testDeleteAuthorById() {
        Long authorId = 1L;

        doNothing().when(authorRepository).deleteById(authorId);

        authorService.deleteAuthorById(authorId);

        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    void testGetAuthorById() {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        author.setName("John Doe");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.getAuthorById(authorId);

        assertEquals(Optional.of(author), result);
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

        when(authorRepository.findAuthorsByNameOrderedByNewsCount(name)).thenReturn(authors);

        List<Author> result = authorService.searchAuthorsByName(name);

        assertEquals(authors, result);
        verify(authorRepository, times(1)).findAuthorsByNameOrderedByNewsCount(name);
    }


}