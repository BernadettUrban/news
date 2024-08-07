package com.mjc.school.controllers;

import com.mjc.school.services.AuthorServiceImpl;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;
import com.mjc.school.sortfield.AuthorSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorServiceImpl authorService;

    public AuthorRestController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all authors", description = "Get a list of all authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all authors",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<AuthorDTO>> getAuthors() {
        List<AuthorDTO> authorDTOS = authorService.listAllAuthors();
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new author", description = "Add a new author to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new author",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateAuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody CreateAuthorDTO createAuthorDTO) {
        AuthorDTO authorDTO = authorService.createAuthor(createAuthorDTO);
        return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{authorId}")
    @Operation(summary = "Delete an author by ID", description = "Remove an author from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the author"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteAuthor(@PathVariable("authorId") Long authorId) {
        authorService.deleteAuthorById(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{authorId}")
    @Operation(summary = "Retrieve an author by ID", description = "Get details of a specific author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the author",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthorDTO> getAuthorById(@Valid @PathVariable("authorId") Long authorId) {
        AuthorDTO authorDTO = authorService.getAuthorById(authorId);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Search authors by name", description = "Search authors using their name with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved authors",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<AuthorDTO>> getAuthorsByName(
            @RequestParam String name,
            Pageable pageable) {
        Page<AuthorDTO> authors = authorService.getAuthorsByName(name, pageable);
        return ResponseEntity.ok(authors);
    }

    @PutMapping("/{authorId}")
    @Operation(summary = "Update an existing author", description = "Modify details of an existing author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the author",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthorDTO> updateAuthor(@Valid @PathVariable Long authorId, @Valid @RequestBody CreateAuthorDTO createAuthorDTO) {
        AuthorDTO authorDTO = authorService.updateAuthor(authorId, createAuthorDTO);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @GetMapping("/newscount")
    @Operation(summary = "Retrieve authors with news count", description = "Get authors sorted by news count with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved authors with news count",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<AuthorDTO>> getAuthorsWithNewsCount(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NEWS_COUNT_DESC") AuthorSortField sortField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortField.getDirection(), sortField.getField()));
        Page<AuthorDTO> authors = authorService.getAuthorsWithNewsCount(pageable);
        return ResponseEntity.ok(authors);
    }
}
