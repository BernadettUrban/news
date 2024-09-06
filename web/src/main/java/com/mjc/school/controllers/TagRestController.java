package com.mjc.school.controllers;

import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.CreateTagDTO;
import com.mjc.school.dtos.TagDTO;
import com.mjc.school.services.TagService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagRestController {
    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/tags",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<CreateTagDTO> createTag(@Valid @RequestBody CreateTagDTO createTagDTO) {
        Tag tag = tagService.createTagFromDTO(createTagDTO);
        tagService.saveTag(tag);
        return new ResponseEntity<>(createTagDTO, HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/api/tags/{tagId}"
    )
    public ResponseEntity<Void> deleteTag(@Valid @PathVariable("tagId") Long tagId) {
        tagService.deleteTagById(tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/tags/{tagId}",
            produces = {"application/json"}
    )
    public ResponseEntity<TagDTO> getTagById(@PathVariable("tagId") Long tagId) {
        TagDTO tagDTO = tagService.getTagById(tagId);
        return new ResponseEntity<>(tagDTO, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/tags",
            produces = {"application/json"}
    )
    public ResponseEntity<Page<TagDTO>> getTags(
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<TagDTO> tagDTOs = tagService.listAllTags(page, size);
        return new ResponseEntity<>(tagDTOs, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/tags/search",
            produces = {"application/json"}
    )
    public ResponseEntity<Page<TagDTO>> searchTagsByName(
            @Valid @RequestParam(value = "name", required = true) String name,
            @Valid @RequestParam(value = "page", defaultValue = "0") int page,
            @Valid @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<TagDTO> tagDTOs = tagService.searchTagsByName(name, page, size);
        return new ResponseEntity<>(tagDTOs, HttpStatus.OK);
    }
}
