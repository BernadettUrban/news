package com.mjc.school.news.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.mjc.school.news.model.AuthorModel;
import com.mjc.school.news.model.CommentModel;
import com.mjc.school.news.model.NewsTagModel;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * NewsModel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-02T11:23:18.059228700+02:00[Europe/Budapest]")
public class NewsModel {

  private Long id;

  private String title;

  private String newsContent;

  private AuthorModel author;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime created;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime modified;

  @Valid
  private List<@Valid NewsTagModel> newstags;

  @Valid
  private List<@Valid CommentModel> comments;

  public NewsModel id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
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

  public NewsModel title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  
  @Schema(name = "title", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public NewsModel newsContent(String newsContent) {
    this.newsContent = newsContent;
    return this;
  }

  /**
   * Get newsContent
   * @return newsContent
  */
  
  @Schema(name = "newsContent", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("newsContent")
  public String getNewsContent() {
    return newsContent;
  }

  public void setNewsContent(String newsContent) {
    this.newsContent = newsContent;
  }

  public NewsModel author(AuthorModel author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  */
  @Valid 
  @Schema(name = "author", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("author")
  public AuthorModel getAuthor() {
    return author;
  }

  public void setAuthor(AuthorModel author) {
    this.author = author;
  }

  public NewsModel created(OffsetDateTime created) {
    this.created = created;
    return this;
  }

  /**
   * Get created
   * @return created
  */
  @Valid 
  @Schema(name = "created", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("created")
  public OffsetDateTime getCreated() {
    return created;
  }

  public void setCreated(OffsetDateTime created) {
    this.created = created;
  }

  public NewsModel modified(OffsetDateTime modified) {
    this.modified = modified;
    return this;
  }

  /**
   * Get modified
   * @return modified
  */
  @Valid 
  @Schema(name = "modified", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("modified")
  public OffsetDateTime getModified() {
    return modified;
  }

  public void setModified(OffsetDateTime modified) {
    this.modified = modified;
  }

  public NewsModel newstags(List<@Valid NewsTagModel> newstags) {
    this.newstags = newstags;
    return this;
  }

  public NewsModel addNewstagsItem(NewsTagModel newstagsItem) {
    if (this.newstags == null) {
      this.newstags = new ArrayList<>();
    }
    this.newstags.add(newstagsItem);
    return this;
  }

  /**
   * Get newstags
   * @return newstags
  */
  @Valid 
  @Schema(name = "newstags", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("newstags")
  public List<@Valid NewsTagModel> getNewstags() {
    return newstags;
  }

  public void setNewstags(List<@Valid NewsTagModel> newstags) {
    this.newstags = newstags;
  }

  public NewsModel comments(List<@Valid CommentModel> comments) {
    this.comments = comments;
    return this;
  }

  public NewsModel addCommentsItem(CommentModel commentsItem) {
    if (this.comments == null) {
      this.comments = new ArrayList<>();
    }
    this.comments.add(commentsItem);
    return this;
  }

  /**
   * Get comments
   * @return comments
  */
  @Valid 
  @Schema(name = "comments", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("comments")
  public List<@Valid CommentModel> getComments() {
    return comments;
  }

  public void setComments(List<@Valid CommentModel> comments) {
    this.comments = comments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewsModel newsModel = (NewsModel) o;
    return Objects.equals(this.id, newsModel.id) &&
        Objects.equals(this.title, newsModel.title) &&
        Objects.equals(this.newsContent, newsModel.newsContent) &&
        Objects.equals(this.author, newsModel.author) &&
        Objects.equals(this.created, newsModel.created) &&
        Objects.equals(this.modified, newsModel.modified) &&
        Objects.equals(this.newstags, newsModel.newstags) &&
        Objects.equals(this.comments, newsModel.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, newsContent, author, created, modified, newstags, comments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewsModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    newsContent: ").append(toIndentedString(newsContent)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    modified: ").append(toIndentedString(modified)).append("\n");
    sb.append("    newstags: ").append(toIndentedString(newstags)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
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

