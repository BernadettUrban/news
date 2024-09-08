package com.mjc.school.repository;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.News;
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        Root<News> root = criteriaQuery.from(News.class);

        List<Predicate> predicates = new ArrayList<>();

        if (tagNames != null && !tagNames.isEmpty()) {
            Join<News, Tag> tags = root.join("tags");
            predicates.add(tags.get("name").in(tagNames));
        }
        if (tagIds != null && !tagIds.isEmpty()) {
            Join<News, Tag> tags = root.join("tags");
            predicates.add(tags.get("id").in(tagIds));
        }
        if (authorName != null && !authorName.isEmpty()) {
            Join<News, Author> author = root.join("author");
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(author.get("name")), "%" + authorName.toLowerCase() + "%"));
        }
        if (title != null && !title.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (content != null && !content.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + content.toLowerCase() + "%"));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        // Create and execute the query
        TypedQuery<News> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<News> newsList = typedQuery.getResultList();

        // Count total results
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<News> countRoot = countQuery.from(News.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        countQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Long> countTypedQuery = entityManager.createQuery(countQuery);
        Long totalElements = countTypedQuery.getSingleResult();

        return new PageImpl<>(newsList, pageable, totalElements);
    }
}
