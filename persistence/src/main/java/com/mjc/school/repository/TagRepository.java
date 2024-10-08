package com.mjc.school.repository;


import com.mjc.school.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TagRepository extends JpaRepository<Tag, Long>, CustomTagRepository {

    Optional<Tag> findByName(String tagName);

    Page<Tag> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
