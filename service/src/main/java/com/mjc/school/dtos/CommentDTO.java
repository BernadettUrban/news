package com.mjc.school.dtos;

import com.mjc.school.domain.News;
import io.swagger.v3.oas.annotations.media.Schema;

public record CommentDTO(
        Long id,
        @Schema(description = "Content of the comment", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
        String commentContent,
        @Schema(description = "ID of the associated news", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
        Long newsId,

        @Schema(description = "Creation timestamp", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
        String created,

        @Schema(description = "Last modified timestamp", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
        String modified
) {
}
