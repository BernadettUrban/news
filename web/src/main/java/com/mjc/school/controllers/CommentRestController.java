package com.mjc.school.controllers;

import com.mjc.school.NewsService;
import com.mjc.school.converters.CommentConverter;
import com.mjc.school.domain.Comment;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.news.api.CommentServiceApi;
import com.mjc.school.news.model.CommentModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CommentRestController implements CommentServiceApi {

    private final NewsService newsService;
    private final CommentConverter commentConverter;

    public CommentRestController(NewsService newsService, CommentConverter commentConverter) {
        this.newsService = newsService;
        this.commentConverter = commentConverter;
    }

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
    }
}
