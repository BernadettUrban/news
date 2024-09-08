package com.mjc.school.services;

import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.TagDTO;
import com.mjc.school.exceptions.CustomException;
import com.mjc.school.exceptions.PaginationException;
import com.mjc.school.mappers.TagMapper;
import com.mjc.school.repository.NewsTagRepository;
import com.mjc.school.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private NewsTagRepository newsTagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listAllTags_shouldReturnPagedTagsAsDTOs() {
        // Arrange
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        Tag tag1 = new Tag(1L, "Tag1");
        Tag tag2 = new Tag(2L, "Tag2");
        Page<Tag> tagPage = new PageImpl<>(Arrays.asList(tag1, tag2), pageable, 2);

        TagDTO tagDTO1 = new TagDTO(1L, "Tag1");
        TagDTO tagDTO2 = new TagDTO(2L, "Tag2");

        when(tagRepository.findAll(pageable)).thenReturn(tagPage);
        when(tagMapper.entityToDTO(tag1)).thenReturn(tagDTO1);
        when(tagMapper.entityToDTO(tag2)).thenReturn(tagDTO2);

        // Act
        Page<TagDTO> result = tagService.listAllTags(page, size);

        // Assert
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        verify(tagRepository, times(1)).findAll(pageable);
        verify(tagMapper, times(1)).entityToDTO(tag1);
        verify(tagMapper, times(1)).entityToDTO(tag2);
    }

    @Test
    void deleteTagById_shouldCallRepositoryDeleteById() {
        // Arrange
        Long tagId = 1L;

        // Act
        tagService.deleteTagById(tagId);

        // Assert
        verify(tagRepository, times(1)).deleteById(tagId);
    }

    @Test
    void getTagById_shouldReturnTagDTO() {
        // Arrange
        Long tagId = 1L;
        Tag tag = new Tag(tagId, "Tag1");
        TagDTO tagDTO = new TagDTO(tagId, "Tag1");

        when(tagRepository.findById(tagId)).thenReturn(java.util.Optional.of(tag));
        when(tagMapper.entityToDTO(tag)).thenReturn(tagDTO);

        // Act
        TagDTO result = tagService.getTagById(tagId);

        // Assert
        assertEquals(tagDTO, result);
        verify(tagRepository, times(1)).findById(tagId);
        verify(tagMapper, times(1)).entityToDTO(tag);
    }

    @Test
    void saveTag_shouldSaveTagUsingRepository() {
        // Arrange
        Tag tag = new Tag(1L, "Tag1");

        // Act
        tagService.saveTag(tag);

        // Assert
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void searchTagsByName_shouldReturnPagedTagDTOs() {
        // Arrange
        String name = "Tag";
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        Tag tag1 = new Tag(1L, "Tag1");
        Tag tag2 = new Tag(2L, "Tag2");
        Page<Tag> tagPage = new PageImpl<>(Arrays.asList(tag1, tag2), pageable, 2);

        TagDTO tagDTO1 = new TagDTO(1L, "Tag1");
        TagDTO tagDTO2 = new TagDTO(2L, "Tag2");

        when(tagRepository.findByNameContainingIgnoreCase(name, pageable)).thenReturn(tagPage);
        when(tagMapper.entityToDTO(tag1)).thenReturn(tagDTO1);
        when(tagMapper.entityToDTO(tag2)).thenReturn(tagDTO2);

        // Act
        Page<TagDTO> result = tagService.searchTagsByName(name, page, size);

        // Assert
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        verify(tagRepository, times(1)).findByNameContainingIgnoreCase(name, pageable);
        verify(tagMapper, times(1)).entityToDTO(tag1);
        verify(tagMapper, times(1)).entityToDTO(tag2);
    }

    @Test
    void getTagsByNewsId_shouldReturnPagedTagDTOs() {
        // Arrange
        Long newsId = 1L;
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        Tag tag1 = new Tag(1L, "Tag1");
        Tag tag2 = new Tag(2L, "Tag2");
        Page<Tag> tagPage = new PageImpl<>(Arrays.asList(tag1, tag2), pageable, 2);

        TagDTO tagDTO1 = new TagDTO(1L, "Tag1");
        TagDTO tagDTO2 = new TagDTO(2L, "Tag2");

        when(newsTagRepository.findTagsByNewsId(newsId, pageable)).thenReturn(tagPage);
        when(tagMapper.entityToDTO(tag1)).thenReturn(tagDTO1);
        when(tagMapper.entityToDTO(tag2)).thenReturn(tagDTO2);

        // Act
        Page<TagDTO> result = tagService.getTagsByNewsId(newsId, page, size);

        // Assert
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        verify(newsTagRepository, times(1)).findTagsByNewsId(newsId, pageable);
        verify(tagMapper, times(1)).entityToDTO(tag1);
        verify(tagMapper, times(1)).entityToDTO(tag2);
    }

    @Test
    void getTagsByNewsId_shouldThrowPaginationException_whenPageIsNegative() {
        // Arrange
        Long newsId = 1L;
        int page = -1;
        int size = 2;

        // Act & Assert
        assertThrows(PaginationException.class, () -> tagService.getTagsByNewsId(newsId, page, size));
    }

    @Test
    void getTagsByNewsId_shouldThrowCustomException_whenSizeIsNegative() {
        // Arrange
        Long newsId = 1L;
        int page = 0;
        int size = -1;

        // Act & Assert
        assertThrows(CustomException.class, () -> tagService.getTagsByNewsId(newsId, page, size));
    }


}