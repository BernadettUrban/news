package com.mjc.school;

import com.mjc.school.dtos.NewsDTO;

import java.util.List;
import java.util.Optional;

public interface NewsService {

    List<NewsDTO> listAllNews();

    void deleteNewsById(Long id);

    Optional<NewsDTO> getNewsById(Long id);

    List<NewsDTO> searchNewsByParameters(
            List<String> tagnames,
            List<String> tagids,
            String author,
            String title,
            String content);

}
