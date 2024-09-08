package com.mjc.school.repository;

import com.mjc.school.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorRepositoryCustom {
    Page<Object[]> findAuthorsByName(String name, Pageable pageable);

    Page<Object[]> findAllAuthors(Pageable pageable);
}
