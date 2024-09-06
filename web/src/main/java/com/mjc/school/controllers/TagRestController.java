package com.mjc.school.controllers;

import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.CreateTagDTO;
import com.mjc.school.dtos.TagDTO;
import com.mjc.school.services.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagRestController {
    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @Operation(summary = "Create a tag", description = "Create a new tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateTagDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CreateTagDTO> createTag(@Valid @RequestBody CreateTagDTO createTagDTO) {
        Tag tag = tagService.createTagFromDTO(createTagDTO);
        tagService.saveTag(tag);
        return new ResponseEntity<>(createTagDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{tagId}")
    @Operation(summary = "Delete a tag", description = "Delete a tag by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tag successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    public ResponseEntity<Void> deleteTag(@Valid @PathVariable("tagId") Long tagId) {
        tagService.deleteTagById(tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{tagId}")
    @Operation(summary = "Get tag by ID", description = "Retrieve a tag by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tag",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TagDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    public ResponseEntity<TagDTO> getTagById(@PathVariable("tagId") Long tagId) {
        TagDTO tagDTO = tagService.getTagById(tagId);
        return new ResponseEntity<>(tagDTO, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Retrieve all tags", description = "Get all tags with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tags",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    public ResponseEntity<Page<TagDTO>> getTags(
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<TagDTO> tagDTOs = tagService.listAllTags(page, size);
        return new ResponseEntity<>(tagDTOs, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Search tags by name", description = "Search for tags by their name with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved search results",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    public ResponseEntity<Page<TagDTO>> searchTagsByName(
            @Valid @RequestParam(value = "name", required = true) String name,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<TagDTO> tagDTOs = tagService.searchTagsByName(name, page, size);
        return new ResponseEntity<>(tagDTOs, HttpStatus.OK);
    }
}
