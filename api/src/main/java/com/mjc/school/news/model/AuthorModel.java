package com.mjc.school.news.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * AuthorModel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-28T17:30:38.266519300+02:00[Europe/Budapest]")
public class AuthorModel {

    private Long id;

    private String name;

    public AuthorModel() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public AuthorModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorModel id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
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

    public AuthorModel name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     */
    @NotNull
    @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorModel authorModel = (AuthorModel) o;
        return Objects.equals(this.id, authorModel.id) &&
                Objects.equals(this.name, authorModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthorModel {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

