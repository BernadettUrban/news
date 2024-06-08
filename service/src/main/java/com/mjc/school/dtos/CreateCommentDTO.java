package com.mjc.school.dtos;

import com.mjc.school.domain.News;

public record CreateCommentDTO( String commentContent,
                                Long newsId) {

}
