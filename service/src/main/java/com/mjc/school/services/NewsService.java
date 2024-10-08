package com.mjc.school.services;

import com.mjc.school.dtos.CreateNewsDTO;
import com.mjc.school.dtos.NewsDTO;
import com.mjc.school.sortfield.SortField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface NewsService {

    Page<NewsDTO> listAllNews(int page, int size, SortField sortField,
                              Sort.Direction sortDirection);

    NewsDTO createNews(CreateNewsDTO createNewsDTO);

    NewsDTO updateNews(Long newsId, CreateNewsDTO createNewsDTO);

    void deleteNewsById(Long id);

    Optional<NewsDTO> getNewsById(Long id);


    Page<NewsDTO> searchNewsByParameters(List<String> tagNames,
                                         List<Long> tagIds,
                                         String authorName,
                                         String title,
                                         String content,
                                         SortField sortField,
                                         Sort.Direction sortDirection,
                                         int page, int size);

}
