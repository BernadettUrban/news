package com.mjc.school.controllers;

import com.mjc.school.NewsService;
import com.mjc.school.domain.Author;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final NewsService newsService;

    public AuthorRestController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAuthors() {
        List<Author> authors = newsService.listAllAuthors();

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

}
