package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.CreateNewsDTO;
import com.mjc.school.dtos.NewsDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.mappers.NewsMapper;
import com.mjc.school.repository.AuthorRepository;
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
    private final AuthorRepository authorRepository;

    @Autowired
    public NewsServiceImpl(NewsMapper newsMapper, NewsRepository newsRepository, AuthorRepository authorRepository) {
        this.newsMapper = newsMapper;
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
    }

    public List<NewsDTO> listAllNews() {
        return newsRepository.findAll()
                .stream().map(n -> newsMapper.entityToDTO(n))
                .collect(Collectors.toList());
    }

    @Override
    public NewsDTO createNews(CreateNewsDTO createNewsDTO) {
        News news = new News();
        news.setTitle(createNewsDTO.title());
        news.setContent(createNewsDTO.newsContent());
        String authorName = createNewsDTO.authorName();
        if(!authorRepository.findAuthorByName(authorName).equals(null)){
            news.setAuthor(authorRepository.findAuthorByName(authorName));


        }
        Author author = new Author(authorName);
        authorRepository.save(author);
        news.setAuthor(authorRepository.findAuthorByName(authorName));

        newsRepository.save(news);
        return newsMapper.entityToDTO(news);
    }

    @Override
    public NewsDTO updateNews(Long newsId, CreateNewsDTO createNewsDTO) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new CustomException("Comment not found with id: " + newsId));
        if(createNewsDTO.title()!=null){
            news.setTitle(createNewsDTO.title());

        }
        if(createNewsDTO.newsContent()!=null){
            news.setContent(createNewsDTO.newsContent());

        }
        String authorName = createNewsDTO.authorName();
        if(authorName!=null){
            if(!authorRepository.findAuthorByName(authorName).equals(null)){
                news.setAuthor(authorRepository.findAuthorByName(authorName));
            }
            Author author = new Author(authorName);
            authorRepository.save(author);
            news.setAuthor(authorRepository.findAuthorByName(authorName));
        }

        newsRepository.save(news);
        return newsMapper.entityToDTO(news);
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
