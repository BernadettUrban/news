package com.mjc.school.repository;

import com.mjc.school.domain.News;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface CustomNewsRepository {
    List<News> searchNewsByParameters(List<String> tagNames,
                                      List<String> tagIds,
                                      String authorName,
                                      String title, String content);
}
