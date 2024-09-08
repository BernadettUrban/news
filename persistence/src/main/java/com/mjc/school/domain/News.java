package com.mjc.school.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(unique = true)
    @Size(min = 5, max = 30, message = "Title must be between 5 and 30")
    private String title;

    @Column(name = "content")
    @Size(min = 5, max = 255, message = "Content must be between 5 and 255")
    private String newsContent;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "created", nullable = false) // **Changed: Column for created timestamp**
    private LocalDateTime created; // **Changed: Timestamp type to LocalDateTime**

    @Column(name = "modified", nullable = false) // **Changed: Column for modified timestamp**
    private LocalDateTime modified; // **Changed: Timestamp type to LocalDateTime**

    @JsonIgnore
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NewsTag> newstags = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // Default constructor
    public News() {
        this.created = LocalDateTime.now(); // **Changed: Default value set to LocalDateTime.now()**
        this.modified = LocalDateTime.now(); // **Changed: Default value set to LocalDateTime.now()**
    }

    // Builder pattern
    private News(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.newsContent = builder.newsContent;
        this.author = builder.author;
        this.created = builder.created != null ? builder.created : LocalDateTime.now(); // **Changed: Handle LocalDateTime**
        this.modified = builder.modified != null ? builder.modified : LocalDateTime.now(); // **Changed: Handle LocalDateTime**
        this.newstags = builder.newstags != null ? builder.newstags : new HashSet<>();
        this.comments = builder.comments != null ? builder.comments : new ArrayList<>();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String newsContent;
        private Author author;
        private LocalDateTime created; // **Changed: Builder handles LocalDateTime**
        private LocalDateTime modified; // **Changed: Builder handles LocalDateTime**
        private Set<NewsTag> newstags;
        private List<Comment> comments;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder newsContent(String newsContent) {
            this.newsContent = newsContent;
            return this;
        }

        public Builder author(Author author) {
            this.author = author;
            return this;
        }

        public Builder created(LocalDateTime created) { // **Changed: Set LocalDateTime in Builder**
            this.created = created;
            return this;
        }

        public Builder modified(LocalDateTime modified) { // **Changed: Set LocalDateTime in Builder**
            this.modified = modified;
            return this;
        }

        public Builder newstags(Set<NewsTag> newstags) {
            this.newstags = newstags;
            return this;
        }

        public Builder comments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public News build() {
            return new News(this);
        }
    }

    // Getters and Setters
    public List<Comment> getComments() {
        return comments;
    }

    public Set<NewsTag> getTags() {
        return newstags;
    }

    public void setTags(Set<NewsTag> tags) {
        this.newstags = tags;
    }

    public LocalDateTime getCreated() { // **Changed: Getter for LocalDateTime**
        return created;
    }

    public void setCreated(LocalDateTime created) { // **Changed: Setter for LocalDateTime**
        this.created = created;
    }

    public LocalDateTime getModified() { // **Changed: Getter for LocalDateTime**
        return modified;
    }

    public void setModified(LocalDateTime modified) { // **Changed: Setter for LocalDateTime**
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id) &&
                Objects.equals(title, news.title) &&
                Objects.equals(newsContent, news.newsContent) &&
                Objects.equals(author, news.author) &&
                Objects.equals(created, news.created) &&
                Objects.equals(modified, news.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, newsContent, author, created, modified);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + newsContent + '\'' +
                ", author=" + author +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
