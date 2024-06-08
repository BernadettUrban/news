package com.mjc.school.controllers;

import com.mjc.school.AuthorServiceImpl;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorRestController {


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

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/authors",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody CreateAuthorDTO createAuthorDTO) {
        AuthorDTO authorDTO = authorService.createAuthor(createAuthorDTO);
        return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/api/authors/{authorId}"
    )
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable("authorId") Long authorId
    ) {
        authorService.deleteAuthorById(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/authors/{authorId}",
            produces = {"application/json"}
    )
    public ResponseEntity<AuthorDTO> getAuthorById(@Valid @PathVariable("authorId") Long authorId) {
        AuthorDTO authorDTO = authorService.getAuthorById(authorId);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/authors/search",
            produces = {"application/json"}
    )
    public ResponseEntity<List<AuthorDTO>> searchAuthorsByName(@Valid @RequestParam String name) {

        List<AuthorDTO> authorDTOs = authorService.searchAuthorsByName(name);
        return new ResponseEntity<>(authorDTOs, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/authors/{authorId}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<AuthorDTO> updateAuthor(@Valid @PathVariable Long authorId, @Valid @RequestBody CreateAuthorDTO createAuthorDTO) {

        AuthorDTO authorDTO = authorService.updateAuthor(authorId, createAuthorDTO);

        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }
    
}
