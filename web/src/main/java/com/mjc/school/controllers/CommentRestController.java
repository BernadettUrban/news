package com.mjc.school.controllers;

import com.mjc.school.CommentService;
import com.mjc.school.dtos.CommentDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/api/comments/{commentId}"
    )
    public ResponseEntity<Void> deleteComments(@Valid @PathVariable("commentId")Long commentId) {
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/comments/{commentId}",
            produces = {"application/json"}
    )

    public ResponseEntity<CommentDTO> getCommentById(@Valid @PathVariable("commentId")Long commentId) {
        CommentDTO commentDTO = commentService.getCommentById(commentId);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

}
