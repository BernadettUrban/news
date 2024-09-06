package com.mjc.school.controllers;

import com.mjc.school.dtos.UpdateCommentDTO;
import com.mjc.school.services.CommentService;
import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.dtos.CreateCommentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete a comment", description = "Deletes a comment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<Void> deleteComments(@Valid @PathVariable("commentId") Long commentId) {
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "Get a comment by ID", description = "Retrieves a comment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comment",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<CommentDTO> getCommentById(@Valid @PathVariable("commentId") Long commentId) {
        CommentDTO commentDTO = commentService.getCommentById(commentId);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Create a new comment", description = "Creates a new comment with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CreateCommentDTO createCommentDTO) {
        CommentDTO commentDTOCreated = commentService.createComment(createCommentDTO);
        return new ResponseEntity<>(commentDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "Update an existing comment", description = "Updates a comment by its ID with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CommentDTO> updateComment(
            @Valid @PathVariable("commentId") Long commentId,
            @Valid @RequestBody UpdateCommentDTO updateCommentDTO) {
        CommentDTO commentDTOCreated = commentService.updateComment(commentId, updateCommentDTO);
        return new ResponseEntity<>(commentDTOCreated, HttpStatus.OK);
    }

}
