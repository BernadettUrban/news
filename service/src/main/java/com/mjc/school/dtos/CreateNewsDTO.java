package com.mjc.school.dtos;

import com.mjc.school.domain.Author;

import java.util.List;

public record CreateNewsDTO(
        String title,
        String newsContent,
        String authorName,
        List<String> tagNames
) {
}
