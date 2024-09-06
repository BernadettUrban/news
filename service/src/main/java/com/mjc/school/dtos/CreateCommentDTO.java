package com.mjc.school.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateCommentDTO(
        @Schema(description = "Content of the comment", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
        String commentContent,
        Long newsId) {

}
