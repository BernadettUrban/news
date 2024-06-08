package com.mjc.school.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorRestControllerTest {

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/authors";
    }

    @Test
    public void createAuthor() {
        String endpoint = getBaseUrl();
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
        String endpoint = getBaseUrl() + "/{id}";
        var response = given().pathParam("id", 1).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void getAuthorById() {
        String endpoint = getBaseUrl() + "/{id}";
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
        String endpoint = getBaseUrl();
        given().
                when().
                get(endpoint).
                then().
                log().
                headers().
                assertThat().
                statusCode(200).
                header("Content-Type", equalTo("application/json")).
                body("size()", greaterThan(0)).
                body("id", everyItem(notNullValue())).
                body("name", everyItem(notNullValue()));
    }

    @Test
    public void searchAuthorsByName() {
        String endpoint = getBaseUrl() + "/search";
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
        String endpoint = getBaseUrl() + "/{id}";
        String body = """
                {
                    "name": "Jane Doe"
                }
                """;
        var response = given().pathParam("id", 1).body(body).when().put(endpoint).then();
        response.log().body();
    }


}


