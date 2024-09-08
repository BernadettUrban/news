package com.mjc.school.repository;

import com.mjc.school.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface NewsRepository extends JpaRepository<News, Long>, CustomNewsRepository {

    @Query("SELECT COUNT(n) FROM News n WHERE n.author.id = :authorId")
    long countByAuthorId(@Param("authorId") Long authorId);

}
