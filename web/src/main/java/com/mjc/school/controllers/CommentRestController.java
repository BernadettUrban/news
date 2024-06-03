package com.mjc.school.controllers;

import com.mjc.school.NewsService;

import com.mjc.school.domain.Comment;
import com.mjc.school.news.api.CommentServiceApi;
import com.mjc.school.news.model.CommentModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentRestController  {

    private final NewsService newsService;


    public CommentRestController(NewsService newsService) {
        this.newsService = newsService;

    }
/*
    @Override
    public ResponseEntity<Void> deleteComments(Long commentId) {
        newsService.deleteCommentById(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CommentModel> getCommentById(Long commentId) {
        Comment comment = newsService.getCommentById(commentId);
        CommentModel commentModel = commentConverter.createCommentModel(comment);
        return new ResponseEntity<>(commentModel, HttpStatus.OK);
    }*/
}
