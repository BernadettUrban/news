package com.mjc.school.controllers;

import com.mjc.school.news.model.AuthorModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AuthorRestControllerTest {


    private static final String BASE_URL = "http://localhost:8888/api/authors";

    @Test
    public void createAuthor() {
        String endpoint = BASE_URL;
        String body = """
                {
                    "name": "John Doe"
                }
                """;
        var response = given().body(body).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void deleteAuthor() {
        String endpoint = BASE_URL + "/{id}";
        var response = given().pathParam("id", 1).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void getAuthorById() {
        String endpoint = BASE_URL + "/{id}";
        given().
                pathParam("id", 1).
                when().
                get(endpoint).
                then().
                assertThat().
                statusCode(200).
                body("id", equalTo(1)).
                body("name", equalTo("John Doe"));
    }

    @Test
    public void getAuthors() {
        String endpoint = BASE_URL;
        given().
                when().
                get(endpoint).
                then().
                log().
                headers().
                assertThat().
                statusCode(200).
                header("Content-Type", equalTo("application/json; charset=UTF-8")).
                body("size()", greaterThan(0)).
                body("id", everyItem(notNullValue())).
                body("name", everyItem(notNullValue()));
    }

    @Test
    public void searchAuthorsByName() {
        String endpoint = BASE_URL + "/search";
        given().
                queryParam("name", "John").
                when().
                get(endpoint).
                then().
                assertThat().
                statusCode(200).
                body("size()", greaterThan(0)).
                body("id", everyItem(notNullValue())).
                body("name", everyItem(containsString("John")));
    }

    @Test
    public void updateAuthor() {
        String endpoint = BASE_URL + "/{id}";
        String body = """
                {
                    "name": "Jane Doe"
                }
                """;
        var response = given().pathParam("id", 1).body(body).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void createSerializedAuthor() {
        String endpoint = BASE_URL;
        AuthorModel author = new AuthorModel();
        author.setName("Serialized Author");

        var response = given().body(author).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void getDeserializedAuthor() {
        String endpoint = BASE_URL + "/{id}";
        AuthorModel expectedAuthor = new AuthorModel();
        expectedAuthor.setId(1L);
        expectedAuthor.setName("John Doe");

        AuthorModel actualAuthor =
                given().
                        pathParam("id", 1).
                        when().
                        get(endpoint).
                        as(AuthorModel.class);

        assertThat(actualAuthor, samePropertyValuesAs(expectedAuthor));
    }
}


