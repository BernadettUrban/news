package com.mjc.school.repository;


import com.mjc.school.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByNameContainingIgnoreCase(String name);

    Optional<Tag> findByName(String tagName);
}
