package com.mjc.school.dtos;

import com.mjc.school.domain.Author;

public record CreateNewsDTO(
        String title,
        String newsContent,
        String authorName
) {
}
