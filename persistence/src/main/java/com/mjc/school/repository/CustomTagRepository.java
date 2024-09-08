package com.mjc.school.repository;

import com.mjc.school.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomTagRepository {
    Page<Tag> findTagsByName(String name, Pageable pageable);
}
