package com.mjc.school;

import com.mjc.school.dtos.CreateNewsDTO;
import com.mjc.school.dtos.NewsDTO;

import java.util.List;
import java.util.Optional;

public interface NewsService {

    List<NewsDTO> listAllNews();

    NewsDTO createNews(CreateNewsDTO createNewsDTO);
    NewsDTO updateNews(Long newsId, CreateNewsDTO createNewsDTO);

    void deleteNewsById(Long id);

    Optional<NewsDTO> getNewsById(Long id);


    List<NewsDTO> searchNewsByParameters(List<String> tagNames,
                                         List<Long> tagIds,
                                         String authorName,
                                         String title,
                                         String content);
}
