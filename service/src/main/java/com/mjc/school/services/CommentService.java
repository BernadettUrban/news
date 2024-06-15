package com.mjc.school.services;

import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.CreateCommentDTO;
import org.springframework.data.domain.Page;

public interface CommentService {
    void deleteCommentById(Long id);

    CommentDTO getCommentById(Long id);

    CommentDTO createComment(CreateCommentDTO createCommentDTO);

    CommentDTO updateComment(Long commentId, CreateCommentDTO createCommentDTO);

    Page<CommentDTO> getCommentsByNewsId(Long newsId, int page, int size);
}
