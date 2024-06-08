package com.mjc.school;

import com.mjc.school.dtos.NewsDTO;
import com.mjc.school.mappers.NewsMapper;
import com.mjc.school.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsMapper newsMapper, NewsRepository newsRepository) {
        this.newsMapper = newsMapper;
        this.newsRepository = newsRepository;
    }

    public List<NewsDTO> listAllNews() {
        return newsRepository.findAll()
                .stream().map(n -> newsMapper.entityToDTO(n))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public Optional<NewsDTO> getNewsById(Long id) {

        return Optional.ofNullable(newsMapper.entityToDTO(newsRepository.findById(id).get()));
    }

    @Override
    public List<NewsDTO> searchNewsByParameters(List<String> tagnames, List<String> tagids, String author, String title, String content) {
        //List<News> newsList = newsRepository.searchNewsByParameters(tagnames, tagids, author, title, content);
        return null;
        //newsList.stream().map(n -> newsMapper.entityToDTO(n)).collect(Collectors.toList());
    }

}
