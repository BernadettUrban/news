package com.mjc.school.controllers;

import com.mjc.school.dtos.*;
import com.mjc.school.services.AuthorService;
import com.mjc.school.services.CommentService;
import com.mjc.school.services.NewsService;
import com.mjc.school.services.TagService;
import com.mjc.school.sortfield.SortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/news")
public class NewsRestController {
    private final NewsService newsService;
    private final CommentService commentService;
    private final AuthorService authorService;
    private final TagService tagService;

    public NewsRestController(NewsService newsService, CommentService commentService, AuthorService authorService, TagService tagService) {
        this.newsService = newsService;
        this.commentService = commentService;
        this.authorService = authorService;
        this.tagService = tagService;
    }


    @GetMapping
    @Operation(summary = "Retrieve news", description = "Get news articles with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved news",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Page<NewsDTO> getNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "CREATED") SortField sortField,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        return newsService.listAllNews(page, size, sortField, sortDirection);
    }

    @PostMapping
    @Operation(summary = "Create news", description = "Create a new news article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "News successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<NewsDTO> createNews(@Valid @RequestBody CreateNewsDTO createNewsDTO) {
        NewsDTO newsDTO = newsService.createNews(createNewsDTO);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);

    }

    @PatchMapping("/{newsId}")
    @Operation(summary = "Update news", description = "Update an existing news article by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "News successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewsDTO.class))),
            @ApiResponse(responseCode = "404", description = "News article not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<NewsDTO> updateNews(@Valid @PathVariable("newsId") Long newsId, @Valid @RequestBody CreateNewsDTO createNewsDTO) {
        NewsDTO newsDTO = newsService.updateNews(newsId, createNewsDTO);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{newsId}")
    @Operation(summary = "Delete news", description = "Delete a news article by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "News successfully deleted"),
            @ApiResponse(responseCode = "404", description = "News article not found")
    })
    public ResponseEntity<Void> deleteNews(@Valid @PathVariable("newsId") Long newsId) {
        newsService.deleteNewsById(newsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{newsId}")
    @Operation(summary = "Get news by ID", description = "Retrieve a news article by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved news article",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewsDTO.class))),
            @ApiResponse(responseCode = "404", description = "News article not found")
    })
    public ResponseEntity<NewsDTO> getNewsById(@Valid @PathVariable("newsId") Long newsId) {
        NewsDTO newsDTO = newsService.getNewsById(newsId).get();
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @GetMapping("/{newsId}/comments")
    @Operation(summary = "Get comments by news ID", description = "Retrieve comments for a news article by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "News article not found")
    })

    public ResponseEntity<Page<CommentDTO>> getCommentsByNewsId(
            @Valid @PathVariable("newsId") Long newsId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CommentDTO> commentDTOList = commentService.getCommentsByNewsId(newsId, page, size);
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @GetMapping("/{newsId}/tags")
    @Operation(summary = "Get tags by news ID", description = "Retrieve tags for a news article by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tags",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "News article not found")
    })

    public ResponseEntity<Page<TagDTO>> getTagsByNewsId(
            @Valid @PathVariable("newsId") Long newsId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TagDTO> tagDTOList = tagService.getTagsByNewsId(newsId, page, size);
        return new ResponseEntity<>(tagDTOList, HttpStatus.OK);
    }


    @GetMapping("/{newsId}/author")
    @Operation(summary = "Get author by news ID", description = "Retrieve the author of a news article by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved author",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "404", description = "News article not found")
    })
    public ResponseEntity<AuthorDTO> getAuthorByNewsId(@Valid @PathVariable("newsId") Long newsId) {
        AuthorDTO authorDTO = authorService.getAuthorByNewsId(newsId);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }


    @GetMapping("/search")
    @Operation(summary = "Search news", description = "Search for news articles by various parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved search results",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<NewsDTO>> searchNewsByParameters
            (@Valid @RequestParam(value = "tagnames", required = false) List<String> tagnames,
             @Valid @RequestParam(value = "tagids", required = false) List<Long> tagids,
             @Valid @RequestParam(value = "author", required = false) String author,
             @Valid @RequestParam(value = "title", required = false) String title,
             @Valid @RequestParam(value = "content", required = false) String content,
             @Valid @RequestParam(value = "page", defaultValue = "0") int page,
             @Valid @RequestParam(value = "size", defaultValue = "10") int size,
             @Valid @RequestParam(defaultValue = "created")
             @Schema(implementation = SortField.class)
                     SortField sortField,
             @Valid @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection
            ) {


        Pageable pageable = PageRequest.of(page, size, sortDirection, sortField.getDatabaseFieldName());
        Page<NewsDTO> result = newsService.searchNewsByParameters(
                tagnames, tagids, author, title, content, sortField, sortDirection, page, size);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
