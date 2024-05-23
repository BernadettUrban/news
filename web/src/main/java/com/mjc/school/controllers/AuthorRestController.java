package com.mjc.school.controllers;

import com.mjc.school.NewsService;
import com.mjc.school.converters.AuthorConverter;
import com.mjc.school.domain.Author;
import com.mjc.school.news.api.AuthorServiceApi;
import com.mjc.school.news.model.AuthorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class AuthorRestController implements AuthorServiceApi {

    private final NewsService newsService;
    private final AuthorConverter authorConverter;

    public AuthorRestController(NewsService newsService, AuthorConverter authorConverter) {
        this.newsService = newsService;
        this.authorConverter = authorConverter;
    }


    public ResponseEntity<List<AuthorModel>> getAuthors() {
        List<Author> authors = newsService.listAllAuthors();
        List<AuthorModel> authorModels = authorConverter.createListOfAuthorModels(authors);
        return new ResponseEntity<>(authorModels, HttpStatus.OK);
    }

}
