package com.mjc.school;

import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.CreateCommentDTO;

public interface CommentService {
    void deleteCommentById(Long id);

    CommentDTO getCommentById(Long id);

    CommentDTO createComment(CreateCommentDTO createCommentDTO);
}
