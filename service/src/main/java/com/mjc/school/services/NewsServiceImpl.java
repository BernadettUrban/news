package com.mjc.school.services;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.News;
import com.mjc.school.domain.NewsTag;
import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.CreateNewsDTO;
import com.mjc.school.dtos.NewsDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.mappers.NewsMapper;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.sortfield.SortField;
import com.mjc.school.specification.NewsSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final Validator validator;

    @Autowired
    public NewsServiceImpl(NewsMapper newsMapper, NewsRepository newsRepository, AuthorRepository authorRepository, TagRepository tagRepository, Validator validator) {
        this.newsMapper = newsMapper;
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.validator = validator;
    }

    public Page<NewsDTO> listAllNews(int page, int size, SortField sortField, Sort.Direction sortDirection) {
        validatePagingAndSortingParameters(page, size, sortField, sortDirection);

        Sort sort = createSort(sortField, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<News> newsPage = newsRepository.findAll(pageable);

        List<NewsDTO> newsDTOs = newsPage.getContent()
                .stream()
                .map(newsMapper::entityToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(newsDTOs, pageable, newsPage.getTotalElements());
    }

    private void validatePagingAndSortingParameters(int page, int size, SortField sortField, Sort.Direction sortDirection) {
        if (page < 0) {
            throw new CustomException("Page number cannot be negative.");
        }
        if (size <= 0) {
            throw new CustomException("Page size must be greater than zero.");
        }
        if (sortField == null) {
            throw new CustomException("Sort field cannot be null.");
        }
        if (sortDirection == null) {
            throw new CustomException("Sort direction cannot be null.");
        }
    }

    private Sort createSort(SortField sortField, Sort.Direction sortDirection) {
        SortField field = Optional.ofNullable(sortField).orElse(SortField.CREATED);
        Sort.Direction direction = Optional.ofNullable(sortDirection).orElse(Sort.Direction.DESC);
        return Sort.by(direction, field.getDatabaseFieldName());
    }

    @Transactional
    @Override
    public NewsDTO createNews(CreateNewsDTO createNewsDTO) {
        Author author = findOrCreateAuthor(createNewsDTO.authorName());
        News news = buildNewsFromDTO(createNewsDTO, author);
        validateNews(news);

        long newsCountbyAuthor = newsRepository.countByAuthorId(author.getId());

        newsRepository.save(news);
        return newsMapper.entityToDTO(news);
    }

    private Author findOrCreateAuthor(String authorName) {
        return authorRepository.findByName(authorName)
                .orElseGet(() -> authorRepository.save(new Author.Builder()
                        .name(authorName)
                        .build()));
    }

    private News buildNewsFromDTO(CreateNewsDTO createNewsDTO, Author author) {
        News news = new News.Builder()
                .title(createNewsDTO.title())
                .newsContent(createNewsDTO.newsContent())
                .author(author)
                .build();
        addTagsToNews(news, createNewsDTO.tagNames());
        news.setCreated(LocalDateTime.now());
        news.setModified(LocalDateTime.now());
        return news;
    }

    private void addTagsToNews(News news, List<String> tagNames) {
        tagNames.forEach(tagName -> {
            Tag tag = findOrCreateTag(tagName);
            news.getTags().add(new NewsTag(news, tag));
        });
    }

    private Tag findOrCreateTag(String tagName) {
        return tagRepository.findByName(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(tagName)));
    }

    private void validateNews(News news) {
        Set<ConstraintViolation<News>> violations = validator.validate(news);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed for news entity", violations);
        }
    }

    @Override
    public NewsDTO updateNews(Long newsId, CreateNewsDTO createNewsDTO) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new CustomException("News not found with id: " + newsId));

        updateNewsDetails(news, createNewsDTO);
        news.setModified(LocalDateTime.now());
        newsRepository.save(news);

        return newsMapper.entityToDTO(news);
    }

    private void updateNewsDetails(News news, CreateNewsDTO createNewsDTO) {
        Optional.ofNullable(createNewsDTO.title()).ifPresent(news::setTitle);
        Optional.ofNullable(createNewsDTO.newsContent()).ifPresent(news::setNewsContent);

        if (createNewsDTO.authorName() != null) {
            Author author = findOrCreateAuthor(createNewsDTO.authorName());
            news.setAuthor(author);
        }

        if (createNewsDTO.tagNames() != null) {
            news.getTags().clear();
            addTagsToNews(news, createNewsDTO.tagNames());
        }
    }

    @Override
    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public Optional<NewsDTO> getNewsById(Long id) {

        return Optional.ofNullable(newsMapper.entityToDTO(newsRepository.findById(id).get()));
    }

    @Override
    public Page<NewsDTO> searchNewsByParameters(List<String> tagNames,
                                                List<Long> tagIds, String authorName,
                                                String title, String content,
                                                Pageable pageable) {

        Specification<News> spec = buildNewsSpecification(tagNames, tagIds, authorName, title, content);

        // Fetch results using the specification
        // Fetch results using the specification
        Page<News> newsPage = newsRepository.findAll(spec, pageable);

        // Map entities to DTOs
        List<NewsDTO> newsDTOList = newsPage.getContent().stream()
                .map(newsMapper::entityToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(newsDTOList, pageable, newsPage.getTotalElements());
    }

    private Specification<News> buildNewsSpecification(List<String> tagNames, List<Long> tagIds,
                                                       String authorName, String title,
                                                       String content) {
        return Specification.where(NewsSpecification.hasAuthorName(authorName))
                .and(NewsSpecification.hasTitle(title))
                .and(NewsSpecification.hasContent(content))
                .and(NewsSpecification.hasTagNames(tagNames))
                .and(NewsSpecification.hasTagIds(tagIds));
    }


}
