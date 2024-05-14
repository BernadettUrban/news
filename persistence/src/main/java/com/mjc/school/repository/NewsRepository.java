package com.mjc.school.repository;

import com.mjc.school.domain.News;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NewsRepository extends PagingAndSortingRepository<News, Long> {
}
