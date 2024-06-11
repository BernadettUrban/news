package com.mjc.school.controllers;

import com.mjc.school.*;
import com.mjc.school.dtos.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/news",
            produces = {"application/json"}
    )
    public Page<NewsDTO> getNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "CREATED") SortField sortField,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        return newsService.listAllNews(page, size, sortField, sortDirection);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/news",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<NewsDTO> createNews(@Valid @RequestBody CreateNewsDTO createNewsDTO) {
        NewsDTO newsDTO = newsService.createNews(createNewsDTO);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);

    }

    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/api/news/{newsId}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )

    public ResponseEntity<NewsDTO> updateNews(@Valid @PathVariable("newsId") Long newsId, @Valid @RequestBody CreateNewsDTO createNewsDTO) {
        NewsDTO newsDTO = newsService.updateNews(newsId, createNewsDTO);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);

    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/api/news/{newsId}"
    )
    public ResponseEntity<Void> deleteNews(@Valid @PathVariable("newsId") Long newsId) {
        newsService.deleteNewsById(newsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/news/{newsId}",
            produces = {"application/json"}
    )
    public ResponseEntity<NewsDTO> getNewsById(@Valid @PathVariable("newsId") Long newsId) {
        NewsDTO newsDTO = newsService.getNewsById(newsId).get();
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/news/{newsId}/comments",
            produces = {"application/json"}
    )

    public ResponseEntity<List<CommentDTO>> getCommentsByNewsId(@Valid @PathVariable("newsId") Long newsId) {
        List<CommentDTO> commentDTOList = commentService.getCommentsByNewsId(newsId);
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/news/{newsId}/tags",
            produces = {"application/json"}
    )

    public ResponseEntity<List<TagDTO>> getTagsByNewsId(@Valid @PathVariable("newsId") Long newsId) {
        List<TagDTO> tagDTOList = tagService.getTagsByNewsId(newsId);
        return new ResponseEntity<>(tagDTOList, HttpStatus.OK);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/news/{newsId}/author",
            produces = {"application/json"}
    )
    public ResponseEntity<AuthorDTO> getAuthorByNewsId(@Valid @PathVariable("newsId") Long newsId) {
        AuthorDTO authorDTO = authorService.getAuthorByNewsId(newsId);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/news/search",
            produces = {"application/json"}
    )
    public ResponseEntity<Page<NewsDTO>> searchNewsByParameters
            (@Valid @RequestParam(value = "tagnames", required = false) List<String> tagnames,
             @Valid @RequestParam(value = "tagids", required = false) List<Long> tagids,
             @Valid @RequestParam(value = "author", required = false) String author,
             @Valid @RequestParam(value = "title", required = false) String title,
             @Valid @RequestParam(value = "content", required = false) String content,
             @PageableDefault @SortDefault(sort = "created", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        Page<NewsDTO> result = newsService.searchNewsByParameters(
                tagnames, tagids, author, title, content, pageable);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
