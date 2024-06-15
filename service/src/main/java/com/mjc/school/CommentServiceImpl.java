package com.mjc.school;

import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.CreateCommentDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.exceptions.PaginationException;
import com.mjc.school.mappers.CommentMapper;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final NewsService newsService;
    private final CommentMapper commentMapper;
    private final NewsRepository newsRepository;

    public CommentServiceImpl(CommentRepository commentRepository, NewsService newsService, CommentMapper commentMapper, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;

        this.newsService = newsService;

        this.commentMapper = commentMapper;
        this.newsRepository = newsRepository;
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

    @Override
    public CommentDTO updateComment(Long commentId, CreateCommentDTO createCommentDTO) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException("Comment not found with id: " + commentId));

        String content = createCommentDTO.commentContent();
        Long newsId = createCommentDTO.newsId();
        News news = newsRepository.findById(newsId).get();
        comment.setContent(content);
        comment.setNews(news);
        comment.setModified();
        commentRepository.save(comment);
        return commentMapper.entityToDTO(comment);
    }

    @Override
    public Page<CommentDTO> getCommentsByNewsId(Long newsId, int page, int size) {
        if (page < 0) {
            throw new PaginationException("Page index must be not negative");
        }
        if (size < 0) {
            throw new CustomException("Size must be not negative");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentsPage = commentRepository.findAll(pageable);
        List<CommentDTO> commentDTOList = commentsPage.getContent()
                .stream()
                .map(comment -> commentMapper.entityToDTO(comment))
                .collect(Collectors.toList());
        return new PageImpl<>(commentDTOList, pageable, commentsPage.getTotalElements());
    }
}
