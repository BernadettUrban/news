package com.mjc.school.dtos;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.Comment;
import com.mjc.school.domain.NewsTag;

import java.util.List;
import java.util.Set;

public record NewsDTO(
        Long id,
        String title,
        String newsContent,
        Author author,
        String created,
        String modified

) {
}
