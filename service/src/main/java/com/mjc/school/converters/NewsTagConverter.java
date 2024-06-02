package com.mjc.school.converters;

import com.mjc.school.domain.NewsTag;
import com.mjc.school.news.model.NewsTagModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsTagConverter {
    private NewsConverter newsConverter;
    private TagConverter tagConverter;

    @Autowired
    public void setNewsConverter(NewsConverter newsConverter) {
        this.newsConverter = newsConverter;
    }

    @Autowired
    public void setTagConverter(TagConverter tagConverter) {
        this.tagConverter = tagConverter;
    }


    public NewsTagModel createNewsTagModel(NewsTag newsTag) {
        NewsTagModel newsTagModel = new NewsTagModel();
        newsTagModel.setId(newsTagModel.getId());
        newsTagModel.setNews(newsConverter.createNewsModel(newsTag.getNews()));
        newsTagModel.setTag(tagConverter.createTagModelFromTag(newsTag.getTag()));
        return newsTagModel;
    }

    public List<NewsTagModel> createListOfNewsTagModels(List<NewsTag> newsTags) {
        List<NewsTagModel> newsTagModelList =
                newsTags.stream().map(a -> createNewsTagModel(a))
                        .collect(Collectors.toList());
        return newsTagModelList;
    }
    /*
    TODO: update method
     */
}
