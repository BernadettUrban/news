package com.mjc.school.repository;

import com.mjc.school.domain.Author;
import com.mjc.school.projection.AuthorNewsCountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

//@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
@RepositoryRestResource(exported = false)
public interface AuthorRepository extends JpaRepository<Author, Long> {
    //List<Author> findByNameContainingIgnoreCase(String name);
    Page<Author> findByNameContaining(String name, Pageable pageable);
    //Page<Author> findAll(PageRequest pageRequest);

    Optional<Author> findByName(String name);

    @Query("SELECT a FROM Author a WHERE a.name = :name")
    Author findAuthorByName(@Param("name") String name);

    Author findByNewsId(Long newsId);

    @Query("SELECT a FROM Author a JOIN a.news n WHERE a.name LIKE %:name% GROUP BY a ORDER BY COUNT(n) DESC")
    List<Author> findAuthorsByNameOrderedByNewsCount(@Param("name") String name);

    @Query("SELECT a FROM Author a JOIN a.news n GROUP BY a ORDER BY COUNT(n) DESC")
    List<Author> findAuthorsOrderedByNewsCount();

   /* @Query(value = "SELECT a.id AS id, a.name AS name, COUNT(n.id) AS newsCount " +
            "FROM Author a LEFT JOIN News n ON a.id = n.author_id " +
            "GROUP BY a.id, a.name",
            countQuery = "SELECT COUNT(DISTINCT a.id) FROM Author a LEFT JOIN News n ON a.id = n.author_id",
            nativeQuery = true)
    Page<AuthorNewsCountProjection> findAuthorsWithNewsCount(Pageable pageable);

    */
}
