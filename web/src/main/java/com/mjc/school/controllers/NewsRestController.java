package com.mjc.school.controllers;

import com.mjc.school.NewsService;
import com.mjc.school.dtos.NewsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsRestController {
    private final NewsService newsService;

    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/news",
            produces = {"application/json"}
    )
    public ResponseEntity<List<NewsDTO>> getNews() {

        List<NewsDTO> newsModels = newsService.listAllNews();

        return new ResponseEntity<>(newsModels, HttpStatus.OK);
    }

/*
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
    }*/
}
