package com.mjc.school.news.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.Objects;

/**
 * NewsTagModel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-29T17:15:24.135300400+02:00[Europe/Budapest]")
public class NewsTagModel {

    private Long id;

    private NewsModel news;

    private TagModel tag;

    public NewsTagModel id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     */

    @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NewsTagModel news(NewsModel news) {
        this.news = news;
        return this;
    }

    /**
     * Get news
     *
     * @return news
     */
    @Valid
    @Schema(name = "news", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("news")
    public NewsModel getNews() {
        return news;
    }

    public void setNews(NewsModel news) {
        this.news = news;
    }

    public NewsTagModel tag(TagModel tag) {
        this.tag = tag;
        return this;
    }

    /**
     * Get tag
     *
     * @return tag
     */
    @Valid
    @Schema(name = "tag", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("tag")
    public TagModel getTag() {
        return tag;
    }

    public void setTag(TagModel tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewsTagModel newsTagModel = (NewsTagModel) o;
        return Objects.equals(this.id, newsTagModel.id) &&
                Objects.equals(this.news, newsTagModel.news) &&
                Objects.equals(this.tag, newsTagModel.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, news, tag);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NewsTagModel {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    news: ").append(toIndentedString(news)).append("\n");
        sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

