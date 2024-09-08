package com.mjc.school.repository;

import com.mjc.school.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

//@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
@RepositoryRestResource(exported = false)
public interface AuthorRepository extends JpaRepository<Author, Long>, AuthorRepositoryCustom {
    Author findByNewsId(Long newsId);


    Page<Author> findByNameContaining(String name, Pageable pageable);

    Optional<Author> findByName(String authorName);

}
