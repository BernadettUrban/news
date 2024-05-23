package com.mjc.school.news.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * NewsModel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-23T18:16:03.571046500+02:00[Europe/Budapest]")
public class NewsModel {

  private Long id;

  private String title;

  private String newsContent;

  private AuthorModel author;

  private String created;

  private String modified;

  public NewsModel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public NewsModel(Long id, String title, String newsContent, AuthorModel author, String created, String modified) {
    this.id = id;
    this.title = title;
    this.newsContent = newsContent;
    this.author = author;
    this.created = created;
    this.modified = modified;
  }

  public NewsModel id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
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
  @NotNull 
  @Schema(name = "title", requiredMode = Schema.RequiredMode.REQUIRED)
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
  @NotNull 
  @Schema(name = "newsContent", requiredMode = Schema.RequiredMode.REQUIRED)
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
  @NotNull @Valid 
  @Schema(name = "author", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("author")
  public AuthorModel getAuthor() {
    return author;
  }

  public void setAuthor(AuthorModel author) {
    this.author = author;
  }

  public NewsModel created(String created) {
    this.created = created;
    return this;
  }

  /**
   * Get created
   * @return created
  */
  @NotNull 
  @Schema(name = "created", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("created")
  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public NewsModel modified(String modified) {
    this.modified = modified;
    return this;
  }

  /**
   * Get modified
   * @return modified
  */
  @NotNull 
  @Schema(name = "modified", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("modified")
  public String getModified() {
    return modified;
  }

  public void setModified(String modified) {
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
    NewsModel newsModel = (NewsModel) o;
    return Objects.equals(this.id, newsModel.id) &&
        Objects.equals(this.title, newsModel.title) &&
        Objects.equals(this.newsContent, newsModel.newsContent) &&
        Objects.equals(this.author, newsModel.author) &&
        Objects.equals(this.created, newsModel.created) &&
        Objects.equals(this.modified, newsModel.modified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, newsContent, author, created, modified);
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

