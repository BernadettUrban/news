package com.mjc.school;

import com.mjc.school.dtos.NewsDTO;
import com.mjc.school.mappers.NewsMapper;
import com.mjc.school.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsServiceOne {
    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsMapper newsMapper, NewsRepository newsRepository) {
        this.newsMapper = newsMapper;
        this.newsRepository = newsRepository;
    }

    public List<NewsDTO> listAllNews() {
       return newsRepository.findAll()
                .stream().map(n-> newsMapper.entityToDTO(n))
                .collect(Collectors.toList());
    }
}
