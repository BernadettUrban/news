package com.mjc.school.controllers;

import com.mjc.school.AuthorServiceImpl;
import com.mjc.school.NewsService;

import com.mjc.school.dtos.AuthorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorRestController  {



    private final AuthorServiceImpl authorService;

    public AuthorRestController(AuthorServiceImpl authorService) {


        this.authorService = authorService;
    }


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/authors",
            produces = {"application/json"}
    )
    public ResponseEntity<List<AuthorDTO>> getAuthors() {
        List<AuthorDTO> authorDTOS = authorService.listAllAuthors();
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }


/*
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
        List<Author> authors = newsService.getAuthorsOrderedByNewsCount();
        //.listAllAuthors();
        List<AuthorModel> authorModels = authorConverter.createListOfAuthorModels(authors);
        return new ResponseEntity<>(authorModels, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AuthorModel>> searchAuthorsByName(String name) {
        List<Author> authors = newsService.searchAuthorsByName(name);
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
    */


}
