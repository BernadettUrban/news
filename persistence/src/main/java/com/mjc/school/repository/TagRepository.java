package com.mjc.school.repository;

import com.mjc.school.domain.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
}
