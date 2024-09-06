package com.mjc.school.dtos;

import com.mjc.school.domain.Author;
import io.swagger.v3.oas.annotations.media.Schema;

public record NewsDTO(
        Long id,
        String title,
        @Schema(description = "Content of the news", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
        String newsContent,
        Author author,
        String created,
        String modified
) {
}
