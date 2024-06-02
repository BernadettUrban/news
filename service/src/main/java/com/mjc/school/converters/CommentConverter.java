package com.mjc.school.converters;

import com.mjc.school.domain.Comment;
import com.mjc.school.news.model.CommentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentConverter {
    private NewsConverter newsConverter;

    @Autowired
    public void setNewsConverter(NewsConverter newsConverter) {
        this.newsConverter = newsConverter;
    }

    public CommentModel createCommentModel(Comment comment) {
        CommentModel commentModel = new CommentModel();
        commentModel.setId(comment.getId());
        commentModel.setCommentContent(comment.getContent());
        // commentModel.setNews(newsConverter.createNewsModel(comment.getNews()));
        commentModel.setCreated(OffsetDateTime.parse(comment.getCreated()));
        commentModel.setModified(OffsetDateTime.parse(comment.getModified()));

        return commentModel;
    }

    public List<CommentModel> createListOfCommentModels(List<Comment> comments) {
        List<CommentModel> authorModelList =
                comments.stream().map(a -> createCommentModel(a))
                        .collect(Collectors.toList());
        return authorModelList;
    }
    /*
    TODO: update method
     */
}
