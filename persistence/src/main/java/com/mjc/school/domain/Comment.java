package com.mjc.school.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.util.Formatting;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment {

    @Transient
    private final SimpleDateFormat SIMPLEDATEFORMAT = Formatting.SIMPLEDATEFORMAT;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String commentContent;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "news_id")
    private News news;
    private String created;
    private String modified;

    public Comment() {
    }

    public Comment(Long id, String content, News news) {
        this.id = id;
        this.commentContent = content;
        this.news = news;
        setCreated();
        setModified();
    }

    public Comment(String content, News news) {
        this.commentContent = content;
        this.news = news;
        setCreated();
        setModified();
    }

    public Comment(Long id, String commentContent, News news, String created, String modified) {
        this.id = id;
        this.commentContent = commentContent;
        this.news = news;
        this.created = created;
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return commentContent;
    }

    public void setContent(String content) {
        this.commentContent = content;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public void setCreated() {
        this.created = SIMPLEDATEFORMAT.format(new Date());

    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified() {
        this.modified = SIMPLEDATEFORMAT.format(new Date());
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

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + commentContent + '\'' +
                ", news=" + news +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
