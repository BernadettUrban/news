package com.mjc.school.mappers;

import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.dtos.CommentDTO;
import com.mjc.school.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
@Component
public interface CommentMapper {
    @Mapping(target = "newsId", source = "news.id")
    @Mapping(target = "created", source = "created", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "localDateTimeToString")
    CommentDTO entityToDTO(Comment comment);

    @Mapping(target = "news", source = "newsId", qualifiedByName = "newsIdToNewsEntity")
    @Mapping(target = "created", source = "created", qualifiedByName = "stringToLocalDateTime")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "stringToLocalDateTime")
    Comment dtoToEntity(CommentDTO dto);

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        return DateTimeUtil.localDateTimeToString(dateTime);
    }

    @Named("stringToLocalDateTime")
    default LocalDateTime stringToLocalDateTime(String dateTime) {
        return DateTimeUtil.stringToLocalDateTime(dateTime);
    }

    @Named("newsIdToNewsEntity")
    default News newsIdToNewsEntity(Long newsId) {
        if (newsId == null) {
            return null;
        }
        News news = new News();
        news.setId(newsId);
        return news;
    }
}
