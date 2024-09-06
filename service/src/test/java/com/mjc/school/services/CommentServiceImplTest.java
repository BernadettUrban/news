package com.mjc.school.services;

import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.CreateCommentDTO;
import com.mjc.school.dtos.UpdateCommentDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.mappers.CommentMapper;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.util.DateTimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsService newsService;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Long commentId;
    private Long newsId;
    private String commentContent;
    private LocalDateTime created;
    private LocalDateTime modified;
    private Comment comment;
    private CommentDTO commentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        commentId = 1L;
        newsId = 2L; // Example news ID
        commentContent = "Sample comment";
        created = LocalDateTime.of(2024, 1, 1, 10, 0); // Example creation timestamp
        modified = LocalDateTime.of(2024, 1, 2, 10, 0); // Example last modified timestamp

        // Convert LocalDateTime to String using DateTimeUtil
        String createdString = DateTimeUtil.localDateTimeToString(created);
        String modifiedString = DateTimeUtil.localDateTimeToString(modified);

        // Create a Comment object
        comment = new Comment();
        comment.setId(commentId);
        comment.setCommentContent(commentContent);
        comment.setNews(new News()); // Ensure the comment has a news associated
        comment.setCreated(created);
        comment.setModified(modified);

        // Define the expected CommentDTO
        commentDTO = new CommentDTO(
                commentId,
                commentContent,
                newsId,
                createdString,
                modifiedString
        );

        // Mock the repository and mapper
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentMapper.entityToDTO(comment)).thenReturn(commentDTO);
    }
    @Test
    void testDeleteCommentById() {
        Long commentId = 1L;

        // Execute the service method
        commentService.deleteCommentById(commentId);

        // Verify that the deleteById method was called with the correct id
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void testGetCommentById_ShouldReturnCommentDTO_WhenCommentExists() {
        // Execute the service method
        CommentDTO result = commentService.getCommentById(commentId);

        // Assert the result
        assertNotNull(result);
        assertEquals(commentDTO, result);
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentMapper, times(1)).entityToDTO(comment);
    }

    @Test
    void testGetCommentById_ShouldThrowException_WhenCommentNotFound() {
        Long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Execute and assert the exception
        CustomException exception = assertThrows(CustomException.class, () -> commentService.getCommentById(commentId));
        assertEquals("Comment not found with id: 1", exception.getMessage());

        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void testCreateComment_ShouldReturnCommentDTO_WhenNewsExists() {
        CreateCommentDTO createCommentDTO = new CreateCommentDTO("Sample comment", newsId);
        News news = new News();
        Comment comment = new Comment(createCommentDTO.commentContent(), news);
        comment.setId(commentId);
        comment.setCreated(LocalDateTime.now()); // Set current time for creation
        comment.setModified(LocalDateTime.now()); // Set current time for modification
        String createdString = DateTimeUtil.localDateTimeToString(comment.getCreated());
        String modifiedString = DateTimeUtil.localDateTimeToString(comment.getModified());
        CommentDTO commentDTO = new CommentDTO(
                commentId,
                createCommentDTO.commentContent(),
                newsId,
                createdString,
                modifiedString
        );

        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.entityToDTO(comment)).thenReturn(commentDTO);

        // Execute the service method
        CommentDTO result = commentService.createComment(createCommentDTO);

        // Assert the result
        assertNotNull(result);
        assertEquals(commentDTO, result);
        verify(newsRepository, times(1)).findById(newsId);
        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(commentMapper, times(1)).entityToDTO(comment);
    }

    @Test
    void testCreateComment_ShouldThrowException_WhenNewsNotFound() {
        CreateCommentDTO createCommentDTO = new CreateCommentDTO("Sample comment", newsId);

        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());

        // Execute and assert the exception
        CustomException exception = assertThrows(CustomException.class, () -> commentService.createComment(createCommentDTO));
        assertEquals("News not found with id: 2", exception.getMessage());

        verify(newsRepository, times(1)).findById(newsId);
    }

    @Test
    void testUpdateComment_ShouldReturnUpdatedCommentDTO_WhenCommentExists() {
        UpdateCommentDTO updateCommentDTO = new UpdateCommentDTO("Updated comment");
        Comment existingComment = new Comment();
        existingComment.setId(commentId);
        existingComment.setCommentContent(commentContent);
        existingComment.setNews(new News());
        existingComment.setCreated(created);
        existingComment.setModified(modified);
        Comment updatedComment = new Comment();
        updatedComment.setId(commentId);
        updatedComment.setCommentContent(updateCommentDTO.commentContent());
        updatedComment.setNews(new News());
        updatedComment.setCreated(created); // Assuming created time doesn't change on update
        updatedComment.setModified(LocalDateTime.now()); // Update modified time
        String updatedCreatedString = DateTimeUtil.localDateTimeToString(updatedComment.getCreated());
        String updatedModifiedString = DateTimeUtil.localDateTimeToString(updatedComment.getModified());
        CommentDTO updatedCommentDTO = new CommentDTO(
                commentId,
                updateCommentDTO.commentContent(),
                newsId,
                updatedCreatedString,
                updatedModifiedString
        );

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(existingComment)).thenReturn(updatedComment);
        when(commentMapper.entityToDTO(updatedComment)).thenReturn(updatedCommentDTO);

        // Execute the service method
        CommentDTO result = commentService.updateComment(commentId, updateCommentDTO);

        // Assert the result
        assertNotNull(result);
        assertEquals(updatedCommentDTO, result);
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).save(existingComment);
        verify(commentMapper, times(1)).entityToDTO(updatedComment);
    }

    @Test
    void testUpdateComment_ShouldThrowException_WhenCommentNotFound() {
        UpdateCommentDTO updateCommentDTO = new UpdateCommentDTO("Updated comment");

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Execute and assert the exception
        CustomException exception = assertThrows(CustomException.class, () -> commentService.updateComment(commentId, updateCommentDTO));
        assertEquals("Comment not found with id: 1", exception.getMessage());

        verify(commentRepository, times(1)).findById(commentId);
    }
}