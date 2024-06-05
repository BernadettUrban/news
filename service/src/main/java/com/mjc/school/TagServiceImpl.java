package com.mjc.school;

import com.mjc.school.domain.Tag;
import com.mjc.school.mappers.TagMapper;
import com.mjc.school.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<Tag> listAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> searchTagsByName(String name) {
        return tagRepository.findByNameContainingIgnoreCase(name);
    }

}
