package com.mjc.school.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NewsRestControllerTest {

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/news";
    }

    @Test
    public void testGetNews() {
        String endpoint = getBaseUrl();
        given()
                .contentType(ContentType.JSON)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .queryParam("sortField", "CREATED")
                .queryParam("sortDirection", "DESC")
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", hasSize(2)); // Adjust later
    }

    @Test
    public void testGetNewsById() {
        Long newsId = 1L;
        String endpoint = getBaseUrl() + "/{newsId}";
        given()
                .contentType(ContentType.JSON)
                .pathParam("newsId", newsId)
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(newsId.intValue()))
                .body("title", equalTo("Breaking News 1"))
                .body("newsContent", equalTo("Content of breaking news 1"));
    }

    @Test
    void createNews() {
        String endpoint = getBaseUrl();
        String requestBody = """
                {
                   "title": "Title",
                   "newsContent": "Content",
                   "authorName": "Author",
                   "tagNames": ["breaking-news", "important"]
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("Title"));
    }

    @Test
    void updateNews() {
        Long newsId = 1L;
        String endpoint = getBaseUrl() + "/{newsId}";
        String requestBody = """
                {
                   "title": "Updated Title",
                   "newsContent": "Content",
                   "authorName": "Author",
                   "tagNames": ["breaking-news", "important"]
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .pathParam("newsId", newsId)
                .body(requestBody)
                .when()
                .patch(endpoint)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("Updated Title"));
    }

    @Test
    void testDeleteNews() {
        long newsId = 1;
        String endpoint = getBaseUrl() + "/{newsId}";
        given()
                .pathParam("newsId", newsId)
                .when()
                .delete(endpoint)
                .then()
                .statusCode(204);
    }


    @Test
    void testGetCommentsByNewsId() {
        long newsId = 1;
        String endpoint = getBaseUrl() + "/{newsId}/comments";
        given()
                .pathParam("newsId", newsId)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content[0].commentContent", equalTo("Comment 1 for Breaking News 1"));
    }

    @Test
    void testGetTagsByNewsId() {
        long newsId = 1;
        String endpoint = getBaseUrl() + "/{newsId}/tags";

        given()
                .pathParam("newsId", newsId)
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", hasSize(2))
                .body("content[0].id", equalTo(1))
                .body("content[1].id", equalTo(2));
    }

    @Test
    void testGetAuthorByNewsId() {
        long newsId = 1; // Replace with actual newsId
        String endpoint = getBaseUrl() + "/{newsId}/author";
        given()
                .pathParam("newsId", newsId)
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("name", equalTo("John Doe"));
    }

    @Test
    void searchNewsByParameters() {
        assertTrue(fail());
    }
}