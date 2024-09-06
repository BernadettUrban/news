package com.mjc.school.controllers;

import com.mjc.school.domain.News;
import com.mjc.school.dtos.CreateNewsDTO;
import com.mjc.school.dtos.NewsDTO;
import com.mjc.school.services.NewsService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class NewsServiceAPITest {

    private static final String BASE_URI = "http://localhost";
    private static final String REQUEST_MAPPING_URI = "/api/news";
    private static final String EXPECTED_NEWS_CONTENT = "Financial News";
    private static final String EXPECTED_NEWS_CONTENT_AFTER_UPDATE = "Updated Financial News";

    private Long newsID;

    @Autowired
    private NewsService newsService;

    @BeforeEach
    void setUp() {
        // Create a piece of news for testing
        News news = new News.Builder()
                .title(EXPECTED_NEWS_CONTENT)
                .newsContent("Initial content")
                .build();
        NewsDTO createdNews = newsService.createNews(new CreateNewsDTO(
                news.getTitle(),
                news.getNewsContent(),
                "Author Name",
                List.of("tag1", "tag2")));
        newsID = createdNews.id();
    }

    @AfterEach
    void tearDown() {
        // Delete the piece of news created for testing
        newsService.deleteNewsById(newsID);
    }

    @Test
    public void testGetAllNews() {
        // Specify the base URL to the RESTful service
        RestAssured.baseURI = BASE_URI;

        // Get the RequestSpecification of the request to be sent to the server
        RequestSpecification httpRequest = RestAssured.given().contentType("application/json");

        // Send the GET request
        Response response = httpRequest.get(REQUEST_MAPPING_URI);

        // Verify the status and body of the response received from the server
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.asString());
    }

    @Test
    public void testGetNewsById() {
        // Specify the base URL to the RESTful service
        RestAssured.baseURI = BASE_URI;

        // Get the RequestSpecification of the request to be sent to the server
        RequestSpecification httpRequest = RestAssured.given().contentType("application/json");

        // Send the GET request
        Response response = httpRequest.get(REQUEST_MAPPING_URI + "/" + newsID);

        // Verify the status and body of the response received from the server
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.asString());
        assertEquals(newsID, response.jsonPath().getInt("id"));
        assertEquals(EXPECTED_NEWS_CONTENT, response.jsonPath().getString("title"));
    }

    @Test
    public void testCreateNews() {
        // Specify the base URL to the RESTful service
        RestAssured.baseURI = BASE_URI;

        // Create a piece of news object
        String requestBody = """
                {
                    "title": "Political News",
                    "newsContent": "Content of the political news",
                    "authorName": "Author",
                    "tagNames": ["politics", "elections"]
                }
                """;

        // Get the RequestSpecification of the request to be sent to the server
        RequestSpecification httpRequest = RestAssured.given().contentType("application/json")
                .body(requestBody);

        // Send the POST request
        Response response = httpRequest.post(REQUEST_MAPPING_URI);

        // Verify the status and body of the response received from the server
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.asString());
        long createdNewsId = response.jsonPath().getInt("id");
        assertEquals("Political News", response.jsonPath().getString("title"));

        // Clean up the created news item
        newsService.deleteNewsById(createdNewsId);
    }

    @Test
    public void testUpdateNews() {
        // Specify the base URL to the RESTful service
        RestAssured.baseURI = BASE_URI;

        // Create a piece of news object with new content
        String requestBody = """
                {
                    "title": "Updated Financial News",
                    "newsContent": "Updated content",
                    "authorName": "Author",
                    "tagNames": ["updated"]
                }
                """;

        // Get the RequestSpecification of the request to be sent to the server
        RequestSpecification httpRequest = RestAssured.given().contentType("application/json")
                .body(requestBody);

        // Send the PUT request
        Response response = httpRequest.put(REQUEST_MAPPING_URI + "/" + newsID);

        // Verify the status and body of the response received from the server
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.asString());
        assertEquals(newsID, response.jsonPath().getInt("id"));
        assertEquals(EXPECTED_NEWS_CONTENT_AFTER_UPDATE, response.jsonPath().getString("title"));
    }

    @Test
    public void testDeleteNews() {
        // Specify the base URL to the RESTful service
        RestAssured.baseURI = BASE_URI;

        // Get the RequestSpecification of the request to be sent to the server
        RequestSpecification httpRequest = RestAssured.given().contentType("application/json");

        // Send the DELETE request
        Response response = httpRequest.delete(REQUEST_MAPPING_URI + "/" + newsID);

        // Verify the status of the response received from the server
        assertEquals(204, response.getStatusCode());
    }
}
