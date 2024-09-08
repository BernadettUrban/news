package com.mjc.school.repository;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.News;
import com.mjc.school.domain.NewsTag;
import com.mjc.school.domain.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


public class CustomNewsRepositoryImpl implements CustomNewsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<News> searchNewsByParameters(List<String> tagNames,
                                             List<Long> tagIds, String authorName,
                                             String title, String content,
                                             Pageable pageable) {
        // JPQL Query with explicit casting and handling of parameters
        String jpql = "SELECT n FROM News n " +
                "LEFT JOIN n.newstags nt " +
                "LEFT JOIN nt.tag t " +
                "LEFT JOIN n.author a " +
                "WHERE (:tagNames IS NULL OR t.name IN :tagNames) " +
                "AND (:tagIds IS NULL OR t.id IN :tagIds) " +
                "AND (:authorName IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))) " +
                "AND (:title IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
                "AND (:content IS NULL OR LOWER(n.newsContent) LIKE LOWER(CONCAT('%', :content, '%')))";

        // Create and set parameters for the query
        TypedQuery<News> query = entityManager.createQuery(jpql, News.class);
        query.setParameter("tagNames", tagNames);
        query.setParameter("tagIds", tagIds);
        query.setParameter("authorName", authorName);
        query.setParameter("title", title);
        query.setParameter("content", content);

        // Pagination
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<News> newsList = query.getResultList();

        // Count Query
        String countJpql = "SELECT COUNT(n) FROM News n " +
                "LEFT JOIN n.newstags nt " +
                "LEFT JOIN nt.tag t " +
                "LEFT JOIN n.author a " +
                "WHERE (:tagNames IS NULL OR t.name IN :tagNames) " +
                "AND (:tagIds IS NULL OR t.id IN :tagIds) " +
                "AND (:authorName IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))) " +
                "AND (:title IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
                "AND (:content IS NULL OR LOWER(n.newsContent) LIKE LOWER(CONCAT('%', :content, '%')))";

        // Create and set parameters for the count query
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        countQuery.setParameter("tagNames", tagNames);
        countQuery.setParameter("tagIds", tagIds);
        countQuery.setParameter("authorName", authorName);
        countQuery.setParameter("title", title);
        countQuery.setParameter("content", content);

        Long totalElements = countQuery.getSingleResult();

        return new PageImpl<>(newsList, pageable, totalElements);
    }




}
