package com.mjc.school;

import com.mjc.school.domain.Comment;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.mappers.CommentMapper;
import com.mjc.school.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Comment not found with id: " + id));
    }
}
