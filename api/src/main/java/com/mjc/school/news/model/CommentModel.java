package com.mjc.school.news.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.mjc.school.news.model.NewsModel;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * CommentModel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-02T11:23:18.059228700+02:00[Europe/Budapest]")
public class CommentModel {

  private Long id;

  private String commentContent;

  private NewsModel news;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime created;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime modified;

  public CommentModel id(Long id) {
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

  public CommentModel commentContent(String commentContent) {
    this.commentContent = commentContent;
    return this;
  }

  /**
   * Get commentContent
   * @return commentContent
  */
  
  @Schema(name = "commentContent", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commentContent")
  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

  public CommentModel news(NewsModel news) {
    this.news = news;
    return this;
  }

  /**
   * Get news
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

  public CommentModel created(OffsetDateTime created) {
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

  public CommentModel modified(OffsetDateTime modified) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentModel commentModel = (CommentModel) o;
    return Objects.equals(this.id, commentModel.id) &&
        Objects.equals(this.commentContent, commentModel.commentContent) &&
        Objects.equals(this.news, commentModel.news) &&
        Objects.equals(this.created, commentModel.created) &&
        Objects.equals(this.modified, commentModel.modified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, commentContent, news, created, modified);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    commentContent: ").append(toIndentedString(commentContent)).append("\n");
    sb.append("    news: ").append(toIndentedString(news)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    modified: ").append(toIndentedString(modified)).append("\n");
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

