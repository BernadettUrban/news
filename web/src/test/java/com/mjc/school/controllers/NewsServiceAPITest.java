package com.mjc.school.controllers;
import com.mjc.school.services.NewsService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class NewsServiceAPITest {

    private static final String BASE_URI = "http://localhost";
    private static final String REQUEST_MAPPING_URI = "/api/news";
    private static final String EXPECTED_NEWS_CONTENT = "Breaking News 1";
    private static final String EXPECTED_NEWS_CONTENT_AFTER_UPDATE = "Updated Breaking News 1";

    @Autowired
    private NewsService newsService;

    @LocalServerPort
    private int port;

    @Test
    public void testGetAllNews() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;

        RequestSpecification httpRequest = RestAssured.given().contentType("application/json");
        Response response = httpRequest.get(REQUEST_MAPPING_URI);

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.asString());
    }

    @Test
    public void testGetNewsById() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;

        Long newsID = 1L; // Pre-existing news ID from your dataset
        RequestSpecification httpRequest = RestAssured.given().contentType("application/json");
        Response response = httpRequest.get(REQUEST_MAPPING_URI + "/" + newsID);

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.asString());
        assertEquals(newsID, response.jsonPath().getLong("id"));
        assertEquals(EXPECTED_NEWS_CONTENT, response.jsonPath().getString("title"));
    }

    @Test
    public void testCreateNews() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;

        String requestBody = """
                {
                    "title": "Political News",
                    "newsContent": "Content of the political news",
                    "authorName": "Jane Smith",
                    "tagNames": ["Tag 1", "Tag 2"]
                }
                """;

        RequestSpecification httpRequest = RestAssured.given().contentType("application/json")
                .body(requestBody);

        Response response = httpRequest.post(REQUEST_MAPPING_URI);

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.asString());
        long createdNewsId = response.jsonPath().getLong("id");
        assertEquals("Political News", response.jsonPath().getString("title"));

        // Clean up the created news item
        newsService.deleteNewsById(createdNewsId);
    }

    @Test
    public void testUpdateNews() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;

        Long newsID = 1L; // Pre-existing news ID
        String requestBody = """
                {
                    "title": "Updated Breaking News 1",
                    "newsContent": "Updated content",
                    "authorName": "John Doe",
                    "tagNames": ["Tag 1"]
                }
                """;

        RequestSpecification httpRequest = RestAssured.given().contentType("application/json")
                .body(requestBody);

        Response response = httpRequest.patch(REQUEST_MAPPING_URI + "/" + newsID);

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.asString());
        assertEquals(newsID, response.jsonPath().getLong("id"));
        assertEquals(EXPECTED_NEWS_CONTENT_AFTER_UPDATE, response.jsonPath().getString("title"));
    }

    @Test
    public void testDeleteNews() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;

        // Create a piece of news for deletion
        String requestBody = """
                {
                    "title": "Temporary News",
                    "newsContent": "Temporary content",
                    "authorName": "Jane Smith",
                    "tagNames": ["Tag 2"]
                }
                """;
        RequestSpecification httpRequest = RestAssured.given().contentType("application/json")
                .body(requestBody);
        Response createResponse = httpRequest.post(REQUEST_MAPPING_URI);
        Long createdNewsId = createResponse.jsonPath().getLong("id");

        // Send the DELETE request
        Response deleteResponse = httpRequest.delete(REQUEST_MAPPING_URI + "/" + createdNewsId);

        assertEquals(204, deleteResponse.getStatusCode());
    }
}
