package com.mjc.school.dtos;

import com.mjc.school.domain.News;

public record CommentDTO(
        Long id,
        String commentContent,
        News news,
        String created,
        String modified
) {
}
