package com.mjc.school.converters;

import com.mjc.school.domain.Tag;
import com.mjc.school.news.model.TagModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagConverter {

    public TagModel createTagModelFromTag(Tag tag) {
        TagModel tagModel = new TagModel(tag.getId(), tag.getName());
        return tagModel;
    }

    public List<TagModel> createListOfTagModels(List<Tag> tags) {
        List<TagModel> tagModelList =
                tags.stream().map(t -> createTagModelFromTag(t))
                        .collect(Collectors.toList());
        return tagModelList;
    }

    public void updateTagFromTagModel(Tag tag, TagModel tagModel) {
        tag.setName(tagModel.getName());
    }

}
