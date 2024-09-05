package com.mjc.school.services;

import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.CreateCommentDTO;
import com.mjc.school.dtos.UpdateCommentDTO;
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
        // Fetch the comment by its ID and ensure it exists
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Comment not found with id: " + id));

        // Ensure the comment is associated with a news article
        if (comment.getNews() == null) {
            throw new CustomException("Comment with id: " + id + " is not associated with any news.");
        }

        // Map the comment entity to CommentDTO using the mapper
        return commentMapper.entityToDTO(comment);
    }

    @Override
    public CommentDTO createComment(CreateCommentDTO createCommentDTO) {
        Long newsId = createCommentDTO.newsId();

        // Fetch the News entity using the provided newsId
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new CustomException("News not found with id: " + newsId));

        // Create the Comment entity
        Comment comment = new Comment(createCommentDTO.commentContent(), news);

        // Save the Comment entity
        Comment savedComment = commentRepository.save(comment);

        // Return the CommentDTO representation
        return commentMapper.entityToDTO(savedComment);
    }


    @Override
    public CommentDTO updateComment(Long id, UpdateCommentDTO updateCommentDTO) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Comment not found with id: " + id));

        existingComment.setCommentContent(updateCommentDTO.commentContent());

        // The `@PreUpdate` method in Comment entity will handle setting modified timestamp

        Comment updatedComment = commentRepository.save(existingComment);
        return commentMapper.entityToDTO(updatedComment);
    }

    @Override
    public Page<CommentDTO> getCommentsByNewsId(Long newsId, int page, int size) {
        if (page < 0) {
            throw new PaginationException("Page index must be not negative");
        }
        if (size < 0) {
            throw new CustomException("Size must be not negative");
        }
        // Ensure the news exists before fetching comments
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new CustomException("News not found with id: " + newsId));

        // Fetch the comments associated with the news using pagination
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentsPage = commentRepository.findByNews(news, pageable);

        // Map the fetched comments to CommentDTOs
        List<CommentDTO> commentDTOList = commentsPage.getContent()
                .stream()
                .map(commentMapper::entityToDTO)  // Use method reference for better readability
                .collect(Collectors.toList());

        // Return a paginated result of CommentDTOs
        return new PageImpl<>(commentDTOList, pageable, commentsPage.getTotalElements());
    }
}
