package com.mjc.school.controllers;

import com.mjc.school.TagService;
import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.TagDTO;
import jakarta.validation.Valid;
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
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO tagDTO) {
        Tag tag = tagService.convertDtoToTag(tagDTO);
        tagService.saveTag(tag);
        return new ResponseEntity<>(tagDTO, HttpStatus.CREATED);
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
    public ResponseEntity<List<TagDTO>> getTags() {
        List<TagDTO> tagDTOs = tagService.listAllTags();
        return new ResponseEntity<>(tagDTOs, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/tags/search",
            produces = {"application/json"}
    )
    public ResponseEntity<List<TagDTO>> searchTagsByName(@Valid @RequestParam(value = "name", required = true) String name) {
        List<TagDTO> tagDTOs = tagService.searchTagsByName(name);
        return new ResponseEntity<>(tagDTOs, HttpStatus.OK);
    }

    /*
    @Override

    @Override


    @Override


    @Override


    @Override
    public ResponseEntity<TagModel> updateTag(Long tagId, TagModel tagModel) {
        Optional<Tag> optionalTag = defaultNewsService.getTagById(tagId);
        if (!optionalTag.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Tag tag = optionalTag.get();
        tagConverter.updateTagFromTagModel(tag, tagModel);
        defaultNewsService.saveTag(tag);
        return new ResponseEntity<>(tagModel, HttpStatus.OK);
    }
    */
}
