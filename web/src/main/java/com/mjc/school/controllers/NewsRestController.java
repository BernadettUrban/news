package com.mjc.school.controllers;

import com.mjc.school.NewsService;
import com.mjc.school.converters.NewsConverter;
import com.mjc.school.domain.News;
import com.mjc.school.news.api.NewsServiceApi;
import com.mjc.school.news.model.NewsModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsRestController implements NewsServiceApi {
    private final NewsService newsService;
    private final NewsConverter newsConverter;

    public NewsRestController(NewsService newsService, NewsConverter newsConverter) {
        this.newsService = newsService;
        this.newsConverter = newsConverter;
    }


    @Override
    public ResponseEntity<Void> deleteNews(Long newsId) {
        newsService.deleteNewsById(newsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<NewsModel>> getNews() {
        List<News> news = newsService.listAllNews();
        List<NewsModel> newsModels = newsConverter.createListOfNewsModels(news);
        return new ResponseEntity<>(newsModels, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NewsModel> getNewsById(Long newsId) {
        News news = newsService.getNewsById(newsId).get();
        NewsModel newsModel = newsConverter.createNewsModel(news);
        return new ResponseEntity<>(newsModel, HttpStatus.OK);
    }
}
