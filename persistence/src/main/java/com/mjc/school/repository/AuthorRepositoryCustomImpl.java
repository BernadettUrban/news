package com.mjc.school.repository;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.News;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;

public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Object[]> findAuthorsByName(String name, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Author> author = cq.from(Author.class);

        Join<Author, News> newsJoin = author.join("news", JoinType.LEFT);

        cq.multiselect(author.get("id"), author.get("name"), cb.count(newsJoin))
                .where(cb.like(author.get("name"), "%" + name + "%"))
                .groupBy(author.get("id"), author.get("name"))
                .orderBy(cb.desc(cb.count(newsJoin)));

        List<Object[]> results = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = getTotalCount(name);
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<Object[]> findAllAuthors(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Author> author = cq.from(Author.class);

        // Join with news to get the count
        Join<Author, News> newsJoin = author.join("news", JoinType.LEFT);

        cq.multiselect(author.get("id"), author.get("name"), cb.count(newsJoin))
                .groupBy(author.get("id"), author.get("name"))
                .orderBy(cb.desc(cb.count(newsJoin)));

        List<Object[]> results = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = getTotalCount(); // Implement this method to get the total count
        return new PageImpl<>(results, pageable, total);
    }

    private long getTotalCount(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Author> author = cq.from(Author.class);

        Join<Author, News> newsJoin = author.join("news", JoinType.LEFT);

        cq.select(cb.count(author))
                .where(cb.like(author.get("name"), "%" + name + "%"));

        return entityManager.createQuery(cq).getSingleResult();
    }

    private long getTotalCount() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Author> author = cq.from(Author.class);

        cq.select(cb.count(author));

        return entityManager.createQuery(cq).getSingleResult();
    }
}
