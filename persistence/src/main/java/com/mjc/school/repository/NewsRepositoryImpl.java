package com.mjc.school.repository;

import com.mjc.school.domain.News;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

public class NewsRepositoryImpl implements CustomNewsRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<News> searchNewsByParameters(List<String> tagNames, List<String> tagIds, String authorName, String title, String content) {
        StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT n FROM News n ");
        queryBuilder.append("LEFT JOIN FETCH n.author a ");
        queryBuilder.append("LEFT JOIN FETCH n.tags t WHERE 1=1 ");

        if (authorName != null && !authorName.isEmpty()) {
            queryBuilder.append("AND a.name = :authorName ");
        }
        if (title != null && !title.isEmpty()) {
            queryBuilder.append("AND n.title LIKE :title ");
        }
        if (content != null && !content.isEmpty()) {
            queryBuilder.append("AND n.content LIKE :content ");
        }
        if (tagNames != null && !tagNames.isEmpty()) {
            queryBuilder.append("AND t.name IN :tagNames ");
        }
        if (tagIds != null && !tagIds.isEmpty()) {
            queryBuilder.append("AND t.id IN :tagIds ");
        }

        TypedQuery<News> query = entityManager.createQuery(queryBuilder.toString(), News.class);

        if (authorName != null && !authorName.isEmpty()) {
            query.setParameter("authorName", authorName);
        }
        if (title != null && !title.isEmpty()) {
            query.setParameter("title", "%" + title + "%");
        }
        if (content != null && !content.isEmpty()) {
            query.setParameter("content", "%" + content + "%");
        }
        if (tagNames != null && !tagNames.isEmpty()) {
            query.setParameter("tagNames", tagNames);
        }
        if (tagIds != null && !tagIds.isEmpty()) {
            query.setParameter("tagIds", tagIds.stream().map(Long::parseLong).collect(Collectors.toList()));
        }

        return query.getResultList();
    }
}
