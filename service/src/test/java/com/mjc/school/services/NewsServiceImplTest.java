package com.mjc.school.services;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.News;
import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.CreateNewsDTO;
import com.mjc.school.dtos.NewsDTO;
import com.mjc.school.mappers.NewsMapper;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.sortfield.SortField;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class NewsServiceImplTest {

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private NewsServiceImpl newsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewsSuccess() {
        // Given
        String authorName = "Author1";
        CreateNewsDTO createNewsDTO = new CreateNewsDTO("Title", "Content", authorName, List.of("Tag1", "Tag2"));

        Author author = new Author.Builder().name(authorName).build();
        News news = new News.Builder().title("Title").newsContent("Content").author(author).build();
        NewsDTO newsDTO = new NewsDTO(1L, "Title", "Content", author, LocalDateTime.now().toString(), LocalDateTime.now().toString());

        // Mocks setup
        when(authorRepository.findByName(authorName)).thenReturn(Optional.of(author));
        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty()); // Simulates that tags need to be created
        when(tagRepository.save(any(Tag.class))).thenReturn(new Tag("Tag1")); // Simplified mock for tag save
        when(validator.validate(any(News.class))).thenReturn(Collections.emptySet());
        when(newsRepository.save(any(News.class))).thenReturn(news);
        when(newsMapper.entityToDTO(any(News.class))).thenReturn(newsDTO);

        // When
        NewsDTO result = newsService.createNews(createNewsDTO);

        // Then
        assertNotNull(result);
        assertEquals("Title", result.title());
        assertEquals("Content", result.newsContent());
        assertEquals(author, result.author());
        assertEquals(newsDTO.created(), result.created());
        assertEquals(newsDTO.modified(), result.modified());
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    void testUpdateNewsSuccess() {
        // Given
        Long newsId = 1L;
        String authorName = "Author1";
        CreateNewsDTO updateDTO = new CreateNewsDTO("Updated Title", "Updated Content", authorName, List.of("Tag1", "Tag2"));

        Author author = new Author.Builder().name(authorName).build();
        News existingNews = new News.Builder().id(newsId).title("Old Title").newsContent("Old Content").author(author).build();
        News updatedNews = new News.Builder().id(newsId).title("Updated Title").newsContent("Updated Content").author(author).build();
        NewsDTO newsDTO = new NewsDTO(newsId, "Updated Title", "Updated Content", author, LocalDateTime.now().toString(), LocalDateTime.now().toString());

        // Mocks setup
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(existingNews));
        when(authorRepository.findByName(authorName)).thenReturn(Optional.of(author));
        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty()); // Simulates that tags need to be created
        when(tagRepository.save(any(Tag.class))).thenReturn(new Tag("Tag1")); // Simplified mock for tag save
        when(validator.validate(any(News.class))).thenReturn(Collections.emptySet());
        when(newsRepository.save(any(News.class))).thenReturn(updatedNews);
        when(newsMapper.entityToDTO(any(News.class))).thenReturn(newsDTO);

        // When
        NewsDTO result = newsService.updateNews(newsId, updateDTO);

        // Then
        assertNotNull(result);
        assertEquals("Updated Title", result.title());
        assertEquals("Updated Content", result.newsContent());
        assertEquals(author, result.author());
        assertEquals(newsDTO.created(), result.created());
        assertEquals(newsDTO.modified(), result.modified());
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    void testDeleteNewsById() {
        // Given
        Long newsId = 1L;

        // Mocks setup
        doNothing().when(newsRepository).deleteById(newsId);

        // When
        newsService.deleteNewsById(newsId);

        // Then
        verify(newsRepository, times(1)).deleteById(newsId);
    }

    @Test
    void testGetNewsById() {
        // Given
        Long newsId = 1L;
        Author author = new Author.Builder().name("Author1").build();
        News news = new News.Builder().id(newsId).title("Title").newsContent("Content").author(author).build();
        NewsDTO newsDTO = new NewsDTO(newsId, "Title", "Content", author, LocalDateTime.now().toString(), LocalDateTime.now().toString());

        // Mocks setup
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));
        when(newsMapper.entityToDTO(any(News.class))).thenReturn(newsDTO);

        // When
        Optional<NewsDTO> result = newsService.getNewsById(newsId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(newsDTO, result.get());
    }

    @Test
    void testSearchNewsByParameters() {
        // Given
        String authorName = "Author1";
        String title = "Title";
        String content = "Content";
        List<String> tagNames = List.of("Tag1");
        List<Long> tagIds = List.of(1L);

        // Create test entities and DTOs
        Author author = new Author.Builder().name(authorName).build();
        News news = new News.Builder().title(title).newsContent(content).author(author).build();
        NewsDTO newsDTO = new NewsDTO(news.getId(), title, content, author, news.getCreated().toString(), news.getModified().toString());

        // Mocking Pageable and Page results
        int page = 0;
        int size = 10;
        SortField sortField = SortField.CREATED;
        Sort.Direction sortDirection = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField.getDatabaseFieldName()));
        Page<News> newsPage = new PageImpl<>(List.of(news), pageable, 1);
        Page<NewsDTO> newsDTOPage = new PageImpl<>(List.of(newsDTO), pageable, 1);

        // Mocks setup
        when(newsRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(newsPage);
        when(newsMapper.entityToDTO(any(News.class))).thenReturn(newsDTO);

        // When
        Page<NewsDTO> result = newsService.searchNewsByParameters(
                tagNames, tagIds, authorName, title, content,
                sortField, sortDirection, page, size);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(newsDTO, result.getContent().get(0));
    }


}
