package com.mjc.school;

import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.TagDTO;

import java.util.List;

public interface TagService {
    List<TagDTO> listAllTags();

    void deleteTagById(Long id);

    TagDTO getTagById(Long id);

    void saveTag(Tag tag);

    Tag convertDtoToTag(TagDTO tagDTO);

    List<TagDTO> searchTagsByName(String name);

    List<TagDTO> getTagsByNewsId(Long newsId);

}
