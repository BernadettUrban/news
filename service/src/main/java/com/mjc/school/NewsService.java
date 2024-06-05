package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.domain.Tag;
import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.NewsDTO;

import java.util.List;
import java.util.Optional;

public interface NewsService {


    List<Tag> listAllTags();

    void deleteTagById(Long id);

    Optional<Tag> getTagById(Long id);

    void saveTag(Tag tag);

    List<Tag> searchTagsByName(String name);

    List<NewsDTO> listAllNews();

    void deleteNewsById(Long id);

    Optional<News> getNewsById(Long id);

    void deleteCommentById(Long id);

    Comment getCommentById(Long id);
}
