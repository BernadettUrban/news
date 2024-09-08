package com.mjc.school.repository;

import com.mjc.school.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomNewsRepository {

        Page<News> searchNewsByParameters
                (List<String> tagNames,
                 List<Long> tagIds,
                 String authorName,
                 String title,
                 String content,
                 Pageable pageable);


}
