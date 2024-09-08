package com.mjc.school.controllers;

import com.mjc.school.domain.Author;
import com.mjc.school.domain.Comment;
import com.mjc.school.domain.News;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CommentRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NewsRepository newsRepository;

    private Long newsId;


    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/comments";
    }

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        newsRepository.deleteAll();  // Clear existing news data

        // Create and save a News entity
        News news = new News();
        news.setTitle("Test title");
        news.setNewsContent("Test content");

        // Create and save an Author if required
        Author author = new Author();
        author.setName("Test author");
        // news.setAuthor(author); // Set the author if it's required

        newsRepository.save(news); // Save News to generate an ID

        // Create and save a Comment entity associated with the saved News
        Comment commentToBeSaved = new Comment("Initial comment", news);
        commentRepository.save(commentToBeSaved);
    }


    @Test
    public void testDeleteComment() {
        Long commentId = commentRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No comment found"))
                .getId();

        String endpoint = getBaseUrl() + "/{commentId}";
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
        Long commentId = commentRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No comment found"))
                .getId();

        String endpoint = getBaseUrl() + "/{commentId}";
        given()
                .contentType(ContentType.JSON)
                .pathParam("commentId", commentId)
                .when()
                .get(endpoint)
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(commentId.intValue()))
                .body("commentContent", equalTo("Initial comment"));
    }

/*
    @Test
    public void testCreateComment() {
        String endpoint = getBaseUrl();  // Base URL for POST request
        Long existingNewsId = 1L;  // This ID should be an ID of an existing News entity

        String requestBody = """
            {
                "commentContent": "This is a new comment",
                "newsId": """ + existingNewsId + """
            }
            """;

        // Make a POST request to the /api/comments endpoint
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(endpoint)  // POST request to create a new comment
                .then()
                .log().body()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("commentContent", equalTo("This is a new comment"))
                .body("newsId", equalTo(existingNewsId.intValue()));  // Verify the response
    }

    
    @Test
    public void testUpdateComment() {
        Long commentId = commentRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No comment found"))
                .getId();

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
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(commentId.intValue()))
                .body("commentContent", equalTo("This is an updated comment"))
                .body("newsId", equalTo(1));
    }
*/

}
