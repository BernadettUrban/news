package com.mjc.school;

import com.mjc.school.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> listAllTags();

    void deleteTagById(Long id);

    Optional<Tag> getTagById(Long id);

    void saveTag(Tag tag);

    List<Tag> searchTagsByName(String name);

}
