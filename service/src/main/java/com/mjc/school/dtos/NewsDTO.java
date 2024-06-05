package com.mjc.school.dtos;

import com.mjc.school.domain.Author;

public record NewsDTO(
        Long id,
        String title,
        String newsContent,
        Author author,
        String created,
        String modified

) {
}
