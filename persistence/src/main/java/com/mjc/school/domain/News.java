package com.mjc.school.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.util.Formatting;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "news")
public class News {

    @Transient
    SimpleDateFormat SIMPLEDATEFORMAT = Formatting.SIMPLEDATEFORMAT;

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
    @ManyToOne()
    @JoinColumn(name = "author_id")
    private Author author;
    private String created;
    private String modified;
    @JsonIgnore
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NewsTag> newstags = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public News() {
        setCreated();
        setModified();
    }

    public News(Long id, String title, String content, Author author) {
        this.id = id;
        this.title = title;
        this.newsContent = content;
        this.author = author;
        setCreated();
        setModified();
    }

    public News(String title, String content, Author author) {
        this.title = title;
        this.newsContent = content;
        this.author = author;
        setCreated();
        setModified();
    }

    public News(Long id, String title, String content, Author author, String created, String modified, Set<NewsTag> tags, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.newsContent = content;
        this.author = author;
        this.created = created;
        this.modified = modified;
        this.newstags = tags;
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Set<NewsTag> getTags() {
        return newstags;
    }

    public void setTags(Set<NewsTag> tags) {
        this.newstags = tags;
    }

    public void setCreated() {
        this.created = SIMPLEDATEFORMAT.format(new Date());

    }

    public void setModified() {
        this.modified = SIMPLEDATEFORMAT.format(new Date());
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
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
        return Objects.equals(id, news.id) && Objects.equals(title, news.title) && Objects.equals(newsContent, news.newsContent) && Objects.equals(author, news.author) && Objects.equals(created, news.created) && Objects.equals(modified, news.modified);
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
