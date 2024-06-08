package com.mjc.school.repository;

import com.mjc.school.domain.NewsTag;
import com.mjc.school.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsTagRepository extends JpaRepository<NewsTag, Long> {
    @Query("SELECT nt.tag FROM NewsTag nt WHERE nt.news.id = :newsId")
    List<Tag> findTagsByNewsId(@Param("newsId") Long newsId);
}
