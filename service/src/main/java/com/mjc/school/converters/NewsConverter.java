package com.mjc.school.converters;

import com.mjc.school.domain.News;
import com.mjc.school.news.model.NewsModel;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsConverter {
    private final AuthorConverter authorConverter;

    public NewsConverter(AuthorConverter authorConverter) {
        this.authorConverter = authorConverter;
    }

    public NewsModel createNewsModel(News news) {
        NewsModel newsModel = new NewsModel();
        newsModel.setId(news.getId());
        newsModel.setTitle(news.getTitle());
        newsModel.setNewsContent(news.getContent());
        newsModel.setAuthor(authorConverter.createAuthorModel(news.getAuthor()));
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
