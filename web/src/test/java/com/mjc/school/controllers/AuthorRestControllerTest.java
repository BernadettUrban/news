package com.mjc.school.controllers;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
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
                    "name": "Jane Doe"
                }
                """;
        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().body() // This will log the response body
                .statusCode(201) // Asserting the status code is 201 (Created)
                .body("name", equalTo("Jane Doe"));
    }

    @Test
    public void deleteAuthor() {
        String endpoint = getBaseUrl() + "/{id}";
        given().pathParam("id", 1)
                .when().delete(endpoint)
                .then()
                .log().body()
                .statusCode(204);
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

    //@Test
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
    public void testGetAuthorsByName() {
        String endpoint = getBaseUrl() + "/search";
        given()
                .contentType(ContentType.JSON)
                .param("name", "John")
                .param("page", 0)
                .param("size", 10)
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .body("content", not(empty()))
                .body("content.name", everyItem(containsStringIgnoringCase("John")))
                .extract().response();

    }

    @Test
    public void updateAuthor() {
        String endpoint = getBaseUrl() + "/{id}";
        String body = """
                {
                    "name": "Jane Doe"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(body)
                .when().put(endpoint)
                .then()
                .log().
                headers().
                assertThat().
                statusCode(200).
                body("id", equalTo(1)).
                body("name", equalTo("Jane Doe"));
    }

    @Test
    public void testGetAuthorsWithNewsCount() {
        String endpoint = getBaseUrl() + "/newscount";
        given()
                .contentType(ContentType.JSON)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .queryParam("sortField", "NEWS_COUNT_DESC")
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                //.body("content", hasSize(2)) // should be 10 after adding more entries to db
                .body("content[0].id", notNullValue())
                .body("content[0].name", notNullValue())
                .body("content[0].newsCount", notNullValue());
    }


}


