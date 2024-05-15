package com.mjc.school.domain;

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
    private String content;
    @ManyToOne()
    @JoinColumn(name = "news_id")
    private News news;
    private String created;
    private String modified;

    public Comment() {
    }

    public Comment(Long id, String content, News news) {
        this.id = id;
        this.content = content;
        this.news = news;
        setCreated();
        setModified();
    }

    public Comment(String content, News news) {
        this.content = content;
        this.news = news;
        setCreated();
        setModified();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public void setModified() {
        this.modified = SIMPLEDATEFORMAT.format(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(content, comment.content) && Objects.equals(news, comment.news) && Objects.equals(created, comment.created) && Objects.equals(modified, comment.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, news, created, modified);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", news=" + news +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
