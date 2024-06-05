package com.mjc.school;

import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.NewsDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.mappers.AuthorMapper;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultNewsService implements NewsService {
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final AuthorMapper authorMapper;


    public DefaultNewsService(AuthorRepository authorRepository, TagRepository tagRepository, NewsRepository newsRepository, CommentRepository commentRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
        this.authorMapper = authorMapper;

    }


    @Override
    public List<Tag> listAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> searchTagsByName(String name) {
        return tagRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<NewsDTO> listAllNews() {
      /*  return newsRepository.findAll()
                .stream().map(n-> newsMapper.entityToDTO(n))
                .collect(Collectors.toList());*/
        return null;
    }

    @Override
    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Comment not found with id: " + id));
    }

}
