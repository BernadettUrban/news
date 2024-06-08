package com.mjc.school.controllers;

import com.mjc.school.CommentService;
import com.mjc.school.NewsService;
import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.NewsDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NewsRestController {
    private final NewsService newsService;
    private final CommentService commentService;

    public NewsRestController(NewsService newsService, CommentService commentService) {
        this.newsService = newsService;
        this.commentService = commentService;
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
    public ResponseEntity<NewsDTO> getNewsById(@Valid @PathVariable("newsId")Long newsId) {
       NewsDTO newsDTO = newsService.getNewsById(newsId).get();
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/news/{newsId}/comments",
            produces = {"application/json"}
    )

    public ResponseEntity<List<CommentDTO>> getCommentsByNewsId(@Valid @PathVariable("newsId")Long newsId) {
        List<CommentDTO> commentDTOList = commentService.getCommentsByNewsId(newsId);
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/news/search",
            produces = {"application/json"}
    )
    public ResponseEntity<List<NewsDTO>> searchNewsByParameters
            (@Valid @RequestParam(value = "tagnames", required = false)  List<String> tagnames,
             @Valid @RequestParam(value = "tagids", required = false)  List<String> tagids,
             @Valid @RequestParam(value = "author", required = false) String author,
             @Valid @RequestParam(value = "title", required = false) String title,
             @Valid @RequestParam(value = "content", required = false) String content
            ) {
        List<NewsDTO> result = newsService.searchNewsByParameters(tagnames, tagids, author, title, content);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
