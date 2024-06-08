package com.mjc.school;

import com.mjc.school.domain.Comment;
import com.mjc.school.dtos.CommentDTO;

public interface CommentService {
    void deleteCommentById(Long id);

    CommentDTO getCommentById(Long id);
}
