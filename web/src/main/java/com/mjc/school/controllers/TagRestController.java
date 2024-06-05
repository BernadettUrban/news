package com.mjc.school.controllers;

import com.mjc.school.TagService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagRestController {
    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }




    /*
    @Override
    public ResponseEntity<TagModel> createTag(TagModel tagModel) {
        Tag tag = new Tag();
        tag.setName(tagModel.getName());
        defaultNewsService.saveTag(tag);
        return new ResponseEntity<>(tagModel, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteTag(Long tagId) {
        defaultNewsService.deleteAuthorById(tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<TagModel> getTagById(Long tagId) {
        Tag tag = defaultNewsService.getTagById(tagId).orElse(null);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TagModel tagModel = tagConverter.createTagModelFromTag(tag);
        return new ResponseEntity<>(tagModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TagModel>> getTags() {
        List<Tag> tags = defaultNewsService.listAllTags();
        List<TagModel> tagModelList = tagConverter.createListOfTagModels(tags);
        return new ResponseEntity<>(tagModelList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TagModel>> searchTagsByName(String name) {
        List<Tag> tags = defaultNewsService.searchTagsByName(name);
        List<TagModel> tagModelList = tagConverter.createListOfTagModels(tags);
        return new ResponseEntity<>(tagModelList, HttpStatus.OK);
    }

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
