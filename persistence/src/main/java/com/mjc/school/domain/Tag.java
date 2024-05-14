package com.mjc.school.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tag {
    @Id
    private Long id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "tag")
    private Set<NewsTag> tags;

    public Tag() {
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(Long id, String name, Set<NewsTag> tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

    public Set<NewsTag> getTags() {
        return tags;
    }

    public void setTags(Set<NewsTag> tags) {
        this.tags = tags;
    }

    public Tag(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
