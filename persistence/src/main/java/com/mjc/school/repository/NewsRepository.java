package com.mjc.school.repository;

import com.mjc.school.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
   /* @Query(value = "SELECT DISTINCT n.* FROM news n " +
            "LEFT JOIN author a ON n.author_id = a.id " +
            "LEFT JOIN newstag nt ON n.id = nt.news_id " +
            "LEFT JOIN tag t ON nt.tag_id = t.id " +
            "WHERE (:authorName IS NULL OR a.name = :authorName) " +
            "AND (:title IS NULL OR n.title LIKE %:title%) " +
            "AND (:content IS NULL OR n.content LIKE %:content%) " +
            "AND (:tagNames IS NULL OR t.name IN :tagNames) " +
            "AND (:tagIds IS NULL OR t.id IN :tagIds)",
            nativeQuery = true)
    List<News> searchNewsByParameters(@Param("tagNames") List<String> tagNames,
                                      @Param("tagIds") List<String> tagIds,
                                      @Param("authorName") String authorName,
                                      @Param("title") String title,
                                      @Param("content") String content);*/
}
