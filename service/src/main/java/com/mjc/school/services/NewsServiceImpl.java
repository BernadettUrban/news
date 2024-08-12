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
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
        if (sortField == null) {
            sortField = SortField.CREATED;
        }
        if (sortDirection == null) {
            sortDirection = Sort.Direction.DESC;
        }

        Sort sort = Sort.by(sortDirection, sortField.getDatabaseFieldName());

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<News> newsPage = newsRepository.findAll(pageable);

        List<NewsDTO> newsDTOs = newsPage.getContent()
                .stream()
                .map(n -> newsMapper.entityToDTO(n))
                .collect(Collectors.toList());

        return new PageImpl<>(newsDTOs, pageable, newsPage.getTotalElements());
    }

    @Override
    public NewsDTO createNews(CreateNewsDTO createNewsDTO) {
        Author author = authorRepository.findByName(createNewsDTO.authorName())
                .orElseGet(() -> authorRepository.save(new Author.Builder()
                        .name(createNewsDTO.authorName())
                        .build()));

        News news = new News();
        news.setTitle(createNewsDTO.title());
        news.setNewsContent(createNewsDTO.newsContent());
        news.setAuthor(author);
        news.setCreated();
        news.setModified();

        for (String tagName : createNewsDTO.tagNames()) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> tagRepository.save(new Tag(tagName)));
            news.getTags().add(new NewsTag(news, tag));
        }

        // Validate the news entity before saving
        Set<ConstraintViolation<News>> violations = validator.validate(news);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed for news entity", violations);
        }


        newsRepository.save(news);
        return newsMapper.entityToDTO(news);
    }

    @Override
    public NewsDTO updateNews(Long newsId, CreateNewsDTO createNewsDTO) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new CustomException("Comment not found with id: " + newsId));

        if (createNewsDTO.title() != null) {
            news.setTitle(createNewsDTO.title());
        }

        if (createNewsDTO.newsContent() != null) {
            news.setNewsContent(createNewsDTO.newsContent());
        }

        if (createNewsDTO.authorName() != null) {
            Author author = authorRepository.findByName(createNewsDTO.authorName())
                    .orElseGet(() -> authorRepository.save(new Author.Builder()
                            .name(createNewsDTO.authorName())
                            .build()));
            news.setAuthor(author);
        }

        if (createNewsDTO.tagNames() != null) {
            news.getTags().clear();
            for (String tagName : createNewsDTO.tagNames()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                news.getTags().add(new NewsTag(news, tag));
            }
        }

        news.setModified();
        newsRepository.save(news);
        return newsMapper.entityToDTO(news);
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
        Specification<News> specification = Specification.where(NewsSpecification.hasAuthorName(authorName))
                .and(NewsSpecification.hasTitle(title))
                .and(NewsSpecification.hasContent(content))
                .and(NewsSpecification.hasTagNames(tagNames))
                .and(NewsSpecification.hasTagIds(tagIds));
        Page<News> newsPage = newsRepository.findAll(specification, pageable);
        List<NewsDTO> newsDTOList = newsPage.getContent().stream()
                .map(newsMapper::entityToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(newsDTOList, pageable, newsPage.getTotalElements());
    }


}
