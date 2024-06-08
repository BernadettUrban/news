package com.mjc.school.repository;

import com.mjc.school.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByNameContainingIgnoreCase(String name);
    Author findByNewsId(Long newsId);

    @Query("SELECT a FROM Author a JOIN a.news n WHERE a.name LIKE %:name% GROUP BY a ORDER BY COUNT(n) DESC")
    List<Author> findAuthorsByNameOrderedByNewsCount(@Param("name") String name);

    @Query("SELECT a FROM Author a JOIN a.news n GROUP BY a ORDER BY COUNT(n) DESC")
    List<Author> findAuthorsOrderedByNewsCount();
}
