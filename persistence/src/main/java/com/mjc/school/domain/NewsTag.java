package com.mjc.school.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class NewsTag {
    @Id
    private Long id;
    private News news;
    private Tag tag;


    public NewsTag() {
    }

    public NewsTag(Long id, News news, Tag tag) {
        this.id = id;
        this.news = news;
        this.tag = tag;
    }

    public NewsTag(News news, Tag tag) {
        this.news = news;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsTag newsTag = (NewsTag) o;
        return Objects.equals(id, newsTag.id) && Objects.equals(news, newsTag.news) && Objects.equals(tag, newsTag.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, news, tag);
    }

    @Override
    public String toString() {
        return "NewsTag{" +
                "id=" + id +
                ", news=" + news +
                ", tag=" + tag +
                '}';
    }
}
