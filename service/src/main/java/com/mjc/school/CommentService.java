package com.mjc.school;

import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.CreateCommentDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CommentService {
    void deleteCommentById(Long id);

    CommentDTO getCommentById(Long id);

    CommentDTO createComment(CreateCommentDTO createCommentDTO);
    CommentDTO updateComment(Long commentId, CreateCommentDTO createCommentDTO);
    List<CommentDTO> getCommentsByNewsId(Long newsId);
}
