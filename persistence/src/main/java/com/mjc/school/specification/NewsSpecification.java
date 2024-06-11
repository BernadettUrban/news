package com.mjc.school.specification;

import com.mjc.school.domain.News;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class NewsSpecification {

    private NewsSpecification() {}

    public static Specification<News> hasAuthorName(String authorName) {
        return (root, query, builder) -> {
            if (authorName == null || authorName.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.join("author", JoinType.LEFT).get("name"), authorName);
        };
    }

    public static Specification<News> hasTitle(String title) {
        return (root, query, builder) -> {
            if (title == null || title.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(root.get("title"), "%" + title + "%");
        };
    }

    // Filter by news content
    public static Specification<News> hasContent(String content) {
        return (root, query, builder) -> {
            if (content == null || content.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(root.get("content"), "%" + content + "%");
        };
    }

    public static Specification<News> hasTagNames(List<String> tagNames) {
        return (root, query, builder) -> {
            if (tagNames == null || tagNames.isEmpty()) {
                return builder.conjunction();
            }
            Join<Object, Object> tags = root.join("tags", JoinType.LEFT);
            return tags.get("name").in(tagNames);
        };
    }


    public static Specification<News> hasTagIds(List<Long> tagIds) {
        return (root, query, builder) -> {
            if (tagIds == null || tagIds.isEmpty()) {
                return builder.conjunction();
            }
            Join<Object, Object> tags = root.join("tags", JoinType.LEFT);
            return tags.get("id").in(tagIds);
        };
    }
}

