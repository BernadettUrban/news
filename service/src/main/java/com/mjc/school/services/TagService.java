package com.mjc.school.services;

import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.CreateTagDTO;
import com.mjc.school.dtos.TagDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TagService {
    List<TagDTO> listAllTags();

    void deleteTagById(Long id);

    TagDTO getTagById(Long id);

    void saveTag(Tag tag);

    Tag convertDtoToTag(TagDTO tagDTO);
    Tag createTagFromDTO(CreateTagDTO createTagDTO);

    List<TagDTO> searchTagsByName(String name);

    Page<TagDTO> getTagsByNewsId(Long newsId, int page, int size);

}
