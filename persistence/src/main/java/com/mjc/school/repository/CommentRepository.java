package com.mjc.school.repository;

import com.mjc.school.domain.Comment;
import com.mjc.school.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByNewsId(Long newsId);
}
