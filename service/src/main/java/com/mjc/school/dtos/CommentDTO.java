package com.mjc.school.dtos;

import com.mjc.school.domain.News;
import io.swagger.v3.oas.annotations.media.Schema;

public record CommentDTO(
        Long id,
        @Schema(description = "Content of the comment", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
        String commentContent,
        Long newsId,  // Changed from News to Long for the ID
        String created,
        String modified
) {
}
