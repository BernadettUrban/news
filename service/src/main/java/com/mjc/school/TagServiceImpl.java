package com.mjc.school;

import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.TagDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.exceptions.PaginationException;
import com.mjc.school.mappers.TagMapper;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<TagDTO> getTagsByNewsId(Long newsId, int page, int size) {
        if(page<0){
            throw new PaginationException("Page index must be not negative");
        }if(size<0){
            throw new CustomException("Size must be not negative");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Tag> tagsPage = tagRepository.findAll(pageable);
        List<TagDTO> tagDTOList = tagsPage.stream()
                .map(tag -> tagMapper.entityToDTO(tag))
                .collect(Collectors.toList());
        return new PageImpl<>(tagDTOList, pageable, tagsPage.getTotalElements());
    }

}
