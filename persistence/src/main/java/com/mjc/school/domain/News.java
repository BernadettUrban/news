package com.mjc.school.domain;

import com.mjc.school.util.Formatting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class News {

    SimpleDateFormat SIMPLEDATEFORMAT = Formatting.SIMPLEDATEFORMAT;

           // new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    private Long id;
    private String title;
    private String content;
    private Author author;
    private String created;
    private String modified;


    public News() {
    }

    public News(Long id, String title, String content, Author author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        setCreated();
        setModified();
    }

    public News(String title, String content, Author author) {
        this.title = title;
        this.content = content;
        this.author = author;
        setCreated();
        setModified();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return Objects.equals(id, news.id) && Objects.equals(title, news.title) && Objects.equals(content, news.content) && Objects.equals(author, news.author) && Objects.equals(created, news.created) && Objects.equals(modified, news.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, author, created, modified);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
