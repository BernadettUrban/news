package com.mjc.school.services;

import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.CreateTagDTO;
import com.mjc.school.dtos.TagDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.exceptions.PaginationException;
import com.mjc.school.mappers.TagMapper;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.repository.TagRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final NewsTagRepository newsTagRepository;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper, NewsTagRepository newsTagRepository) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.newsTagRepository = newsTagRepository;
    }


    @Override
    public Page<TagDTO> listAllTags(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page <Tag> tagPage = tagRepository.findAll(pageRequest);
        return tagPage.map(tagMapper::entityToDTO);
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
    public Tag createTagFromDTO(CreateTagDTO createTagDTO) {
        return tagMapper.createTagDTOToEntity(createTagDTO);
    }

    @Override
    public Page<TagDTO> searchTagsByName(String name, int page, int size) {
        // Handle sorting direction

        // Create Pageable object with page, size, and sorting information
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated data from the repository
        Page<Tag> tagPage = tagRepository.findByNameContainingIgnoreCase(name, pageable);

        // Map to DTOs and return the Page of DTOs
        return tagPage.map(tagMapper::entityToDTO);
    }

    @Override
    public Page<TagDTO> getTagsByNewsId(Long newsId, int page, int size) {
        if (page < 0) {
            throw new PaginationException("Page index must be not negative");
        }
        if (size < 0) {
            throw new CustomException("Size must be not negative");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Tag> tagsPage = newsTagRepository.findTagsByNewsId(newsId, pageable);
        List<TagDTO> tagDTOList = tagsPage.stream()
                .map(tag -> tagMapper.entityToDTO(tag))
                .collect(Collectors.toList());
        return new PageImpl<>(tagDTOList, pageable, tagsPage.getTotalElements());
    }

}
