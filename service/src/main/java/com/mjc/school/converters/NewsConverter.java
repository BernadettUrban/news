package com.mjc.school.converters;

import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.domain.NewsTag;
import com.mjc.school.news.model.NewsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NewsConverter {
    private AuthorConverter authorConverter;
    private CommentConverter commentConverter;
    private NewsTagConverter newsTagConverter;

    @Autowired
    public void setAuthorConverter(AuthorConverter authorConverter) {
        this.authorConverter = authorConverter;
    }

    @Autowired
    public void setCommentConverter(@Lazy CommentConverter commentConverter) {
        this.commentConverter = commentConverter;
    }

    @Autowired
    public void setNewsTagConverter(@Lazy NewsTagConverter newsTagConverter) {
        this.newsTagConverter = newsTagConverter;
    }

    public NewsModel createNewsModel(News news) {
        NewsModel newsModel = new NewsModel();
        newsModel.setId(news.getId());
        newsModel.setTitle(news.getTitle());
        newsModel.setNewsContent(news.getContent());
        newsModel.setAuthor(authorConverter.createAuthorModel(news.getAuthor()));
        List<Comment> comments = news.getComments();
        //newsModel.setComments(commentConverter.createListOfCommentModels(comments));
        Set<NewsTag> newsTagSet = news.getTags();
        List<NewsTag> newsTagList = new ArrayList();
        newsTagList.addAll(newsTagSet);
        //newsModel.setNewstags(newsTagConverter.createListOfNewsTagModels(newsTagList));
        newsModel.setCreated(OffsetDateTime.parse(news.getCreated()));
        newsModel.setModified(OffsetDateTime.parse(news.getModified()));
        return newsModel;
    }

    public List<NewsModel> createListOfNewsModels(List<News> news) {
        List<NewsModel> newsModelList =
                news.stream().map(a -> createNewsModel(a))
                        .collect(Collectors.toList());
        return newsModelList;
    }

    public void updateNewsFromUpdateNewsModel(News news, NewsModel newsModel) {
        news.setTitle(newsModel.getTitle());
        /*
        TODO:fix for update method
         */
    }

}
