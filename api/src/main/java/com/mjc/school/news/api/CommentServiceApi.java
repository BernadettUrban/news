/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.mjc.school.news.api;

import com.mjc.school.news.model.CommentModel;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-02T11:56:20.122431200+02:00[Europe/Budapest]")
@Validated
@Tag(name = "CommentService", description = "the CommentService API")
public interface CommentServiceApi {

    /**
     * DELETE /api/comments/{commentId} : Delete a comment item by ID
     *
     * @param commentId ID of the comment item to delete (required)
     * @return Comment deleted successfully (status code 204)
     */
    @Operation(
        operationId = "deleteComments",
        summary = "Delete a comment item by ID",
        tags = { "CommentService" },
        responses = {
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/api/comments/{commentId}"
    )
    
    ResponseEntity<Void> deleteComments(
        @Parameter(name = "commentId", description = "ID of the comment item to delete", required = true, in = ParameterIn.PATH) @PathVariable("commentId") Long commentId
    );


    /**
     * GET /api/comments/{commentId} : Get comment by ID
     *
     * @param commentId ID of the comment item to find (required)
     * @return OK (status code 200)
     */
    @Operation(
        operationId = "getCommentById",
        summary = "Get comment by ID",
        tags = { "CommentService" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CommentModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/comments/{commentId}",
        produces = { "application/json" }
    )
    
    ResponseEntity<CommentModel> getCommentById(
        @Parameter(name = "commentId", description = "ID of the comment item to find", required = true, in = ParameterIn.PATH) @PathVariable("commentId") Long commentId
    );

}
