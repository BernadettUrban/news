package com.mjc.school.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CreateNewsDTO(
        String title,
        @Schema(description = "Content of the news", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
        String newsContent,
        String authorName,
        List<String> tagNames
) {
}
