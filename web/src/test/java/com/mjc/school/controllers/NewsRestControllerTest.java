package com.mjc.school.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional  // Rollback after each test method
class NewsRestControllerTest {

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/news";
    }

   /* @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() throws Exception {
        // Path to the SQL scripts

        String dataSql = new String(Files.readAllBytes(Paths.get("src/test/resources/data.sql")));

        // Execute schema and data SQL scripts

        jdbcTemplate.execute(dataSql);
    }

    */
    @Test
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetTagsByNewsId() {
        long newsId = 1L;
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
    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    public void testSearchNewsByParameters() {
        RestAssured.baseURI = getBaseUrl();
        // Perform the request and verify
        Response response = given()
                .queryParam("tagnames", "Tag 1,Tag 2")
                .queryParam("author", "John Doe")
                .queryParam("title", "Breaking News 1")
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/search")  // Endpoint path
                .then()
                .statusCode(200)
                .body("content", hasSize(2))
                .body("content[0].title", equalTo("Breaking News 1"))
                .body("content[1].title", equalTo("Breaking News 2"))
                .extract().response();
    }


}