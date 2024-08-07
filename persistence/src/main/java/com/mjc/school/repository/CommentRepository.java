package com.mjc.school.repository;

import com.mjc.school.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByNewsId(Long newsId);
}
