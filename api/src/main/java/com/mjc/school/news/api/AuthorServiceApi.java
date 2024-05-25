/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.mjc.school.news.api;

import com.mjc.school.news.model.AuthorModel;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-25T14:21:33.421975600+02:00[Europe/Budapest]")
@Validated
@Tag(name = "AuthorService", description = "the AuthorService API")
public interface AuthorServiceApi {

    /**
     * POST /api/authors : Create a new author
     *
     * @param authorModel Author object to be created (required)
     * @return Created author (status code 201)
     */
    @Operation(
        operationId = "createAuthor",
        summary = "Create a new author",
        tags = { "AuthorService" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Created author", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/api/authors",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<AuthorModel> createAuthor(
        @Parameter(name = "AuthorModel", description = "Author object to be created", required = true) @Valid @RequestBody AuthorModel authorModel
    );


    /**
     * DELETE /api/authors/{authorId} : Delete an author by ID
     *
     * @param authorId ID of the author to delete (required)
     * @return Author deleted successfully (status code 204)
     */
    @Operation(
        operationId = "deleteAuthor",
        summary = "Delete an author by ID",
        tags = { "AuthorService" },
        responses = {
            @ApiResponse(responseCode = "204", description = "Author deleted successfully")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/api/authors/{authorId}"
    )
    
    ResponseEntity<Void> deleteAuthor(
        @Parameter(name = "authorId", description = "ID of the author to delete", required = true, in = ParameterIn.PATH) @PathVariable("authorId") Long authorId
    );


    /**
     * GET /api/authors/{authorId} : Get author by ID
     *
     * @param authorId ID of the author to retrieve (required)
     * @return Retrieved author (status code 200)
     */
    @Operation(
        operationId = "getAuthorById",
        summary = "Get author by ID",
        tags = { "AuthorService" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Retrieved author", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/authors/{authorId}",
        produces = { "application/json" }
    )
    
    ResponseEntity<AuthorModel> getAuthorById(
        @Parameter(name = "authorId", description = "ID of the author to retrieve", required = true, in = ParameterIn.PATH) @PathVariable("authorId") Long authorId
    );


    /**
     * GET /api/authors : List all authors
     *
     * @return List of authors (status code 200)
     */
    @Operation(
        operationId = "getAuthors",
        summary = "List all authors",
        tags = { "AuthorService" },
        responses = {
            @ApiResponse(responseCode = "200", description = "List of authors", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AuthorModel.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/authors",
        produces = { "application/json" }
    )
    
    ResponseEntity<List<AuthorModel>> getAuthors(
        
    );


    /**
     * PUT /api/authors/{authorId} : Update an author by ID
     *
     * @param authorId ID of the author to update (required)
     * @param authorModel Author object with updated information (required)
     * @return Updated author (status code 200)
     */
    @Operation(
        operationId = "updateAuthor",
        summary = "Update an author by ID",
        tags = { "AuthorService" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Updated author", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/api/authors/{authorId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<AuthorModel> updateAuthor(
        @Parameter(name = "authorId", description = "ID of the author to update", required = true, in = ParameterIn.PATH) @PathVariable("authorId") Long authorId,
        @Parameter(name = "AuthorModel", description = "Author object with updated information", required = true) @Valid @RequestBody AuthorModel authorModel
    );

}
