package com.mjc.school.dtos;

public record AuthorDTO(
        Long id,
        String name,
        Long newsCount
) {
    public AuthorDTO {
        if (newsCount == null) {
            newsCount = 0L;
        }
    }
}
