package com.mjc.school;

import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.TagDTO;
import com.mjc.school.mappers.TagMapper;
import com.mjc.school.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<TagDTO> listAllTags() {

        return tagRepository.findAll()
                .stream()
                .map(t -> tagMapper.entityToDTO(t))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public TagDTO getTagById(Long id) {
        return tagMapper.entityToDTO(tagRepository.findById(id).get());
    }

    @Override
    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public Tag convertDtoToTag(TagDTO tagDTO) {
        return tagMapper.dtoToEntity(tagDTO);
    }

    @Override
    public List<TagDTO> searchTagsByName(String name) {

        return tagRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(t -> tagMapper.entityToDTO(t))
                .collect(Collectors.toList());
    }

}
