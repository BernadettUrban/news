package com.mjc.school;

import com.mjc.school.domain.Comment;

public interface CommentService {
    void deleteCommentById(Long id);

    Comment getCommentById(Long id);
}
