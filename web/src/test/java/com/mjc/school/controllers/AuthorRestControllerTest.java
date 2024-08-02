package com.mjc.school.controllers;

import com.mjc.school.domain.Author;
import com.mjc.school.repository.AuthorRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AuthorRepository authorRepository;

    private final String BASE_URL = "http://localhost:";
    private final String AUTHORS_ENDPOINT = "/api/authors";
    private final String CREATE_AUTHOR_BODY = """
            {
                "name": "New Author"
            }
            """;
    private final String UPDATE_AUTHOR_BODY = """
            {
                "name": "Updated Author"
            }
            """;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();

        authorRepository.save(new Author.Builder()
                .name("John Doe")
                .build());

        authorRepository.save(new Author.Builder()
                .name("Jane Doe")
                .build());
    }

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll();
    }

    private String getBaseUrl() {
        return BASE_URL + port;
    }

    @Test
    public void createAuthor() {
        String endpoint = getBaseUrl() + AUTHORS_ENDPOINT;
        given()
                .contentType(ContentType.JSON)
                .body(CREATE_AUTHOR_BODY)
                .when()
                .post(endpoint)
                .then()
                .log().body()
                .statusCode(201)
                .body("name", equalTo("New Author"));
    }

    @Test
    public void deleteAuthor() {
        Long authorId = authorRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> authorRepository.save(new Author.Builder()
                        .name("John Doe")
                        .build())).getId();

        String endpoint = getBaseUrl() + AUTHORS_ENDPOINT + "/{id}";
        given()
                .pathParam("id", authorId)
                .when()
                .delete(endpoint)
                .then()
                .log().body()
                .statusCode(204);
    }

    @Test
    public void getAuthorById() {
        Long authorId = authorRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> authorRepository.save(new Author.Builder()
                        .name("John Doe")
                        .build())).getId();

        String endpoint = getBaseUrl() + AUTHORS_ENDPOINT + "/{id}";
        given()
                .pathParam("id", authorId)
                .when()
                .get(endpoint)
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(authorId.toString())))
                .body("name", notNullValue());
    }

    @Test
    public void getAuthors() {
        String endpoint = getBaseUrl() + AUTHORS_ENDPOINT;
        given()
                .when()
                .get(endpoint)
                .then()
                .log().body()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("id", everyItem(notNullValue()))
                .body("name", everyItem(notNullValue()));
    }

    @Test
    public void updateAuthor() {
        Long authorId = authorRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> authorRepository.save(new Author.Builder()
                        .name("John Doe")
                        .build())).getId();

        String endpoint = getBaseUrl() + AUTHORS_ENDPOINT + "/{id}";
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", authorId)
                .body(UPDATE_AUTHOR_BODY)
                .when()
                .put(endpoint)
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(authorId.toString())))
                .body("name", equalTo("Updated Author"));
    }
}
