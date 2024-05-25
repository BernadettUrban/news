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
import java.util.Optional;

@RestController

public class AuthorRestController implements AuthorServiceApi {

    private final NewsService newsService;
    private final AuthorConverter authorConverter;

    public AuthorRestController(NewsService newsService, AuthorConverter authorConverter) {
        this.newsService = newsService;
        this.authorConverter = authorConverter;
    }


    @Override
    public ResponseEntity<AuthorModel> createAuthor(AuthorModel authorModel) {
        Author author = new Author();
        author.setName(authorModel.getName());
        newsService.saveAuthor(author);
        return new ResponseEntity<>(authorModel, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(Long authorId) {
        newsService.deleteAuthorById(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<AuthorModel> getAuthorById(Long authorId) {
        Author author = newsService.getAuthorById(authorId).orElse(null);
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorModel authorModel = authorConverter.createAuthorModel(author);
        return new ResponseEntity<>(authorModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AuthorModel>> getAuthors() {
        List<Author> authors = newsService.listAllAuthors();
        List<AuthorModel> authorModels = authorConverter.createListOfAuthorModels(authors);
        return new ResponseEntity<>(authorModels, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthorModel> updateAuthor(Long authorId, AuthorModel authorModel) {
        Optional<Author> optionalAuthor = newsService.getAuthorById(authorId);
        if (!optionalAuthor.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        Author author = optionalAuthor.get();
        authorConverter.updateAuthorFromUpdateAuthorModel(author, authorModel);
        newsService.saveAuthor(author);
        return new ResponseEntity<>(authorModel, HttpStatus.OK);
    }


}
