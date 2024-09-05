package com.mjc.school.services;

import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.CreateCommentDTO;
import com.mjc.school.dtos.UpdateCommentDTO;
import org.springframework.data.domain.Page;

public interface CommentService {
    void deleteCommentById(Long id);

    CommentDTO getCommentById(Long id);

    CommentDTO createComment(CreateCommentDTO createCommentDTO);

    CommentDTO updateComment(Long commentId, UpdateCommentDTO updateCommentDTO);

    Page<CommentDTO> getCommentsByNewsId(Long newsId, int page, int size);
}
