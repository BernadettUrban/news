package com.mjc.school.repository;

import com.mjc.school.domain.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class TagRepositoryCustomImpl implements CustomTagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Tag> findTagsByName(String name, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Create CriteriaQuery instance for Tag entity
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> tagRoot = cq.from(Tag.class);

        // Create Predicate for partial name match
        Predicate namePredicate = cb.like(cb.lower(tagRoot.get("name")), "%" + name.toLowerCase() + "%");
        cq.where(namePredicate);

        // Apply ordering
        cq.orderBy(cb.asc(tagRoot.get("name")));

        // Create TypedQuery and set pagination parameters
        TypedQuery<Tag> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Execute query to get paginated results
        List<Tag> tags = query.getResultList();

        // Count the total number of matching tags
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Tag> countRoot = countQuery.from(Tag.class);
        countQuery.select(cb.count(countRoot));
        countQuery.where(namePredicate);
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(tags, pageable, count);
    }
}
