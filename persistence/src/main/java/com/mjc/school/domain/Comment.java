package com.mjc.school.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.util.Formatting;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    @Size(min = 3, max = 255, message = "Content must be between 3 and 255 characters")
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "modified", nullable = false)
    private LocalDateTime modified;

    // Default constructor for JPA
    public Comment() {}

    private Comment(Builder builder) {
        this.id = builder.id;
        this.commentContent = builder.commentContent;
        this.news = builder.news;
        this.created = builder.created;
        this.modified = builder.modified;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now(); // Set creation timestamp
        this.modified = LocalDateTime.now(); // Set initial modification timestamp
    }

    @PreUpdate
    protected void onUpdate() {
        this.modified = LocalDateTime.now(); // Update modification timestamp
    }

    // Builder Pattern
    public static class Builder {
        private Long id;
        private String commentContent;
        private News news;
        private LocalDateTime created;
        private LocalDateTime modified;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withCommentContent(String commentContent) {
            this.commentContent = commentContent;
            return this;
        }

        public Builder withNews(News news) {
            this.news = news;
            return this;
        }

        public Builder withCreated(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public Builder withModified(LocalDateTime modified) {
            this.modified = modified;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(commentContent, comment.commentContent) && Objects.equals(news, comment.news) && Objects.equals(created, comment.created) && Objects.equals(modified, comment.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentContent, news, created, modified);
    }
}
