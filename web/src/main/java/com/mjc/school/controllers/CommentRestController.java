package com.mjc.school.controllers;

import com.mjc.school.services.CommentService;
import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.CreateCommentDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> deleteComments(@Valid @PathVariable("commentId") Long commentId) {
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/comments/{commentId}",
            produces = {"application/json"}
    )

    public ResponseEntity<CommentDTO> getCommentById(@Valid @PathVariable("commentId") Long commentId) {
        CommentDTO commentDTO = commentService.getCommentById(commentId);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/comments",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CreateCommentDTO createCommentDTO) {
        CommentDTO commentDTOCreated = commentService.createComment(createCommentDTO);
        return new ResponseEntity<>(commentDTOCreated, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/comments/{commentId}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<CommentDTO> updateComment(
            @Valid @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CreateCommentDTO createCommentDTO) {
        CommentDTO commentDTOCreated = commentService.updateComment(commentId, createCommentDTO);
        return new ResponseEntity<>(commentDTOCreated, HttpStatus.OK);
    }

}
