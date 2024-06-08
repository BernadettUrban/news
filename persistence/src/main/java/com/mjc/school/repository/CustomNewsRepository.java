package com.mjc.school.repository;

import com.mjc.school.domain.News;

import java.util.List;

public interface CustomNewsRepository {
    List<News> searchNewsByParameters(List<String> tagNames,
                                      List<String> tagIds,
                                      String authorName,
                                      String title, String content);
}
