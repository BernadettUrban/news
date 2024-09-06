package com.mjc.school.dtos;

import com.mjc.school.domain.News;
import com.mjc.school.domain.Tag;

public record NewsTagDTO(
        Long id,
        News news,
        Tag tag
) {
}
