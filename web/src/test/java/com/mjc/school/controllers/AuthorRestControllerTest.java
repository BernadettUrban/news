package com.mjc.school.controllers;

import com.mjc.school.dtos.AuthorDTO;
import com.mjc.school.dtos.CreateAuthorDTO;
import com.mjc.school.services.AuthorService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthorRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AuthorService authorService;

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
    private Long authorId;

    @BeforeEach
    void setUp() {
        // Use the service layer to set up initial data
        authorService.listAllAuthors(0, 10); // Initialize with pagination to ensure list is not empty
        AuthorDTO author1 = authorService.createAuthor(new CreateAuthorDTO("John Doll"));
        AuthorDTO author2 = authorService.createAuthor(new CreateAuthorDTO("Jane Doll"));
        authorId = author1.id();
    }

    @AfterEach
    void tearDown() {
        // Clean up data using the service layer
        authorService.deleteAll();
    }

    private String getBaseUrl() {
        return BASE_URL + port;
    }

    /*@Test
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
        String endpoint = getBaseUrl() + AUTHORS_ENDPOINT + "/{id}";
        given()
                .pathParam("id", authorId)
                .when()
                .get(endpoint)
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(Math.toIntExact(authorId)))
                .body("name", notNullValue());
    }

    @Test
    public void getAuthors() {
        String endpoint = getBaseUrl() + AUTHORS_ENDPOINT + "?page=0&size=10";
        given()
                .when()
                .get(endpoint)
                .then()
                .log().body()
                .statusCode(200)
                .body("content.size()", greaterThan(0))
                .body("content.id", everyItem(notNullValue()))
                .body("content.name", everyItem(notNullValue()));
    }

    @Test
    public void updateAuthor() {
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
                .body("id", equalTo(Math.toIntExact(authorId)))
                .body("name", equalTo("Updated Author"));
    }

     */
}
