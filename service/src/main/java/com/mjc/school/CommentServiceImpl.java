package com.mjc.school;

import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.CreateCommentDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.mappers.CommentMapper;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, NewsRepository newsRepository, NewsRepository newsRepository1, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository1;

        this.commentMapper = commentMapper;
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDTO getCommentById(Long id) {
        return commentMapper.entityToDTO(
        commentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Comment not found with id: " + id)));
    }

    @Override
    public CommentDTO createComment(CreateCommentDTO createCommentDTO) {
        String content = createCommentDTO.commentContent();
        Long newsId = createCommentDTO.newsId();
        News news = newsRepository.findById(newsId).get();
        Comment comment = new Comment(content, news);
        commentRepository.save(comment);
        return commentMapper.entityToDTO(comment);
    }
}
