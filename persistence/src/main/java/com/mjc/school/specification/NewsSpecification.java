package com.mjc.school.specification;

import com.mjc.school.domain.News;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class NewsSpecification {

    private NewsSpecification() {
    }

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
            return builder.like(root.get("newsContent"), "%" + content + "%");
        };
    }

    // Combined method for filtering by tag names and/or tag IDs
    public static Specification<News> hasTags(List<String> tagNames, List<Long> tagIds) {
        return (root, query, builder) -> {
            if ((tagNames == null || tagNames.isEmpty()) && (tagIds == null || tagIds.isEmpty())) {
                return builder.conjunction(); // No filtering by tags if both are empty
            }
            Join<Object, Object> tags = root.join("newstags", JoinType.LEFT);

            if (tagNames != null && !tagNames.isEmpty() && tagIds != null && !tagIds.isEmpty()) {
                return builder.or(
                        tags.get("tag").get("name").in(tagNames),
                        tags.get("tag").get("id").in(tagIds)
                );
            } else if (tagNames != null && !tagNames.isEmpty()) {
                return tags.get("tag").get("name").in(tagNames);
            } else {
                return tags.get("tag").get("id").in(tagIds);
            }
        };
    }
}

