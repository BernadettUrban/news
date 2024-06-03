package com.mjc.school.controllers;

import com.mjc.school.DefaultNewsService;

import com.mjc.school.domain.Tag;
import com.mjc.school.news.api.TagServiceApi;
import com.mjc.school.news.model.TagModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TagRestController  {
    private final DefaultNewsService defaultNewsService;


    public TagRestController(DefaultNewsService defaultNewsService) {
        this.defaultNewsService = defaultNewsService;

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
