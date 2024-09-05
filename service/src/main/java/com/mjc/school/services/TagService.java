package com.mjc.school.services;

import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.CreateTagDTO;
import com.mjc.school.dtos.NewsDTO;
import com.mjc.school.dtos.TagDTO;
import com.mjc.school.sortfield.SortField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TagService {
    List<TagDTO> listAllTags();
    Page<TagDTO> listAllNews(int page, int size);

    void deleteTagById(Long id);

    TagDTO getTagById(Long id);

    void saveTag(Tag tag);

    Tag convertDtoToTag(TagDTO tagDTO);
    Tag createTagFromDTO(CreateTagDTO createTagDTO);


    Page<TagDTO> searchTagsByName(String name, int page, int size);

    Page<TagDTO> getTagsByNewsId(Long newsId, int page, int size);

}
