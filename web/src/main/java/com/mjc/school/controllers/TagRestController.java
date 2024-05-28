package com.mjc.school.controllers;

import com.mjc.school.DefaultNewsService;
import com.mjc.school.converters.TagConverter;
import com.mjc.school.domain.Tag;
import com.mjc.school.news.api.TagServiceApi;
import com.mjc.school.news.model.TagModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagRestController implements TagServiceApi {
    private final DefaultNewsService defaultNewsService;
    private final TagConverter tagConverter;

    public TagRestController(DefaultNewsService defaultNewsService, TagConverter tagConverter) {
        this.defaultNewsService = defaultNewsService;
        this.tagConverter = tagConverter;
    }

    @Override
    public ResponseEntity<TagModel> createTag(TagModel tagModel) {
        Tag tag = new Tag();
        tag.setName(tagModel.getName());
        defaultNewsService.saveTag(tag);
        return new ResponseEntity<>(tagModel, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteTag(Long tagId) {
        return null;
    }

    @Override
    public ResponseEntity<TagModel> getTagById(Long tagId) {
        return null;
    }

    @Override
    public ResponseEntity<List<TagModel>> getTags() {
        return null;
    }

    @Override
    public ResponseEntity<List<TagModel>> searchTagsByName(String name) {
        return null;
    }

    @Override
    public ResponseEntity<TagModel> updateTag(Long tagId, TagModel tagModel) {
        return null;
    }
}
