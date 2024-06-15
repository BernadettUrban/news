package com.mjc.school.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentRestControllerTest {
    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/comments";
    }

    @Test
    public void testDeleteComment() {
        String endpoint = getBaseUrl() + "/{commentId}";
        Long commentId = 1L;
        given()
                .pathParam("commentId", commentId)
                .when()
                .delete(endpoint)
                .then()
                .log().body()
                .statusCode(204);
    }

    @Test
    public void testGetCommentById() {
        Long commentId = 1L;
        String endpoint = getBaseUrl() + "/{commentId}";
        given()
                .contentType(ContentType.JSON)
                .pathParam("commentId", commentId)
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .body("news.id", equalTo(1));
    }

    @Test
    public void testCreateComment() {
        String endpoint = getBaseUrl();
        String requestBody = """
                {
                    "commentContent": "This is a new comment",
                    "newsId": 1
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
                .body("commentContent", equalTo("This is a new comment"))
                .body("news.id", equalTo(1));
    }

    @Test
    public void testUpdateComment() {
        Long commentId = 1L;
        String endpoint = getBaseUrl() + "/{commentId}";
        String requestBody = """
                {
                    "commentContent": "This is an updated comment",
                    "newsId": 1
                }
                """;

        given()
                .pathParam("commentId", commentId)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(endpoint)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("commentContent", equalTo("This is an updated comment"))
                .body("news.id", equalTo(1));
    }
}