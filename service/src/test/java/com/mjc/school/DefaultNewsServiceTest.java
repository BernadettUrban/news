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
    private DefaultNewsService newsService;

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

        List<Author> result = newsService.listAllAuthors();

        assertEquals(authors, result);
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testDeleteAuthorById() {
        Long authorId = 1L;

        doNothing().when(authorRepository).deleteById(authorId);

        newsService.deleteAuthorById(authorId);

        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    void testGetAuthorById() {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        author.setName("John Doe");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Optional<Author> result = newsService.getAuthorById(authorId);

        assertEquals(Optional.of(author), result);
        verify(authorRepository, times(1)).findById(authorId);
    }

    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setName("John Doe");

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        newsService.saveAuthor(author);

        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testSearchAuthorsByName() {
        String name = "John";
        Author author = new Author();
        author.setName("John Doe");
        List<Author> authors = Arrays.asList(author);

        when(authorRepository.findByNameContainingIgnoreCase(name)).thenReturn(authors);

        List<Author> result = newsService.searchAuthorsByName(name);

        assertEquals(authors, result);
        verify(authorRepository, times(1)).findByNameContainingIgnoreCase(name);
    }

    @Test
    void testListAllTags() {
        Tag tag1 = new Tag();
        tag1.setName("Tag1");
        Tag tag2 = new Tag();
        tag2.setName("Tag2");
        List<Tag> tags = Arrays.asList(tag1, tag2);

        when(tagRepository.findAll()).thenReturn(tags);

        List<Tag> result = newsService.listAllTags();

        assertEquals(tags, result);
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void testDeleteTagById() {
        Long tagId = 1L;

        doNothing().when(tagRepository).deleteById(tagId);

        newsService.deleteTagById(tagId);

        verify(tagRepository, times(1)).deleteById(tagId);
    }

    @Test
    void testGetTagById() {
        Long tagId = 1L;
        Tag tag = new Tag();
        tag.setId(tagId);
        tag.setName("Tag");

        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

        Optional<Tag> result = newsService.getTagById(tagId);

        assertEquals(Optional.of(tag), result);
        verify(tagRepository, times(1)).findById(tagId);
    }

    @Test
    void testSaveTag() {
        Tag tag = new Tag();
        tag.setName("Tag");

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        newsService.saveTag(tag);

        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void testSearchTagsByName() {
        String name = "Tag";
        Tag tag = new Tag();
        tag.setName("Tag");
        List<Tag> tags = Arrays.asList(tag);

        when(tagRepository.findByNameContainingIgnoreCase(name)).thenReturn(tags);

        List<Tag> result = newsService.searchTagsByName(name);

        assertEquals(tags, result);
        verify(tagRepository, times(1)).findByNameContainingIgnoreCase(name);
    }


}