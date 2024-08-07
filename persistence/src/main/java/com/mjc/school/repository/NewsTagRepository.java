package com.mjc.school.repository;

import com.mjc.school.domain.NewsTag;
import com.mjc.school.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface NewsTagRepository extends JpaRepository<NewsTag, Long> {
    @Query("SELECT nt.tag FROM NewsTag nt WHERE nt.news.id = :newsId")
    List<Tag> findTagsByNewsId(@Param("newsId") Long newsId);
}
