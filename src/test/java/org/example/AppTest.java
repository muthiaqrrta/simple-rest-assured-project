package org.example;

import io.restassured.response.Response;
import org.example.request.AuthorRequest;
import org.example.request.BookRequest;
import org.example.response.BookResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class AppTest {

    String defaultBaseUrl = "http://localhost:8080";

    @Test
    public void getBookById() {
        String bookId = "83d6cace-83e4-48c0-8293-c1953205c525";

        Response getResponse = given()
                .baseUri(defaultBaseUrl)
                .accept("application/json")
                .contentType("application/json")
                .pathParam("id", bookId)
                .when().log().all().get("/api/book/{id}");
        getResponse.prettyPrint();

        assertEquals("status code is different with expected",
                200, getResponse.getStatusCode());

        BookResponse bookResponse = getResponse.getBody().as(BookResponse.class);

        assertEquals("book id is different with expected",
                bookId, bookResponse.getId());
    }

    @Test
    public void updateBook() {
        String bookId = "a57ba245-9371-496d-bdee-10f65c2f9372";

        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setName("author name 1");
        authorRequest.setAddress("author address 1");
        authorRequest.setPhoneNumber("phone number 1");

        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("book title 1");
        bookRequest.setAuthor(authorRequest);

        Response updateResponse = given()
                .baseUri(defaultBaseUrl)
                .accept("application/json")
                .contentType("application/json")
                .pathParam("id", bookId)
                .body(bookRequest)
                .when().log().all().put("/api/book/{id}");
        updateResponse.getBody().prettyPrint();

        assertEquals("status code is different with expected",
                200, updateResponse.getStatusCode());

        BookResponse bookResponse = updateResponse.getBody().as(BookResponse.class);

        assertEquals("book title is different with expected",
                "book title 1", bookResponse.getTitle());
    }

    @Test
    public void patchBook() {
        String bookId = "1b7846de-3148-45f6-b063-f097f375ea43";

        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("book title 2");

        Response patchResponse = given()
                .baseUri(defaultBaseUrl)
                .accept("application/json")
                .contentType("application/json")
                .pathParam("id", bookId)
                .body("{}")
                .when().log().all().patch("/api/book/{id}");
        patchResponse.getBody().prettyPrint();

        assertEquals("status code is different with expected",
                200, patchResponse.getStatusCode());

        BookResponse bookResponse = patchResponse.getBody().as(BookResponse.class);

        assertEquals("book title is different with expected",
                "book title 2", bookResponse.getTitle());
    }

    @Test
    public void deleteBook() {
        String bookId = "1b7846de-3148-45f6-b063-f097f375ea43";

        Response deleteResponse = given()
                .baseUri(defaultBaseUrl)
                .accept("application/json")
                .contentType("application/json")
                .pathParam("id", bookId)
                .when().log().all().delete("/api/book/{id}");
        deleteResponse.prettyPrint();

        assertEquals("status code is different with expected",
                200, deleteResponse.getStatusCode());
    }

    @Test
    public void getBooks() {
        Response getBooksResponse = given()
                .baseUri(defaultBaseUrl)
                .accept("application/json")
                .contentType("application/json")
                .when().log().all().get("/api/book");
        getBooksResponse.prettyPrint();

        assertEquals("status code is different with expected",
                200, getBooksResponse.getStatusCode());
    }

    @Test
    public void createBook() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setName("new author name 3");
        authorRequest.setAddress("new author address 3");
        authorRequest.setPhoneNumber("new phone number 3");

        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("new book title 3");
        bookRequest.setAuthor(authorRequest);

        Response createResponse = given()
                .baseUri(defaultBaseUrl)
                .accept("application/json")
                .contentType("application/json")
                .body(bookRequest)
                .when().log().all().post("/api/book");
        createResponse.prettyPrint();

        assertEquals("status code is different with expected",
                201, createResponse.getStatusCode());
    }

    @Test
    public void getBookFilter() {
        int page = 0;
        int size = 5;
        String[] sort = new String[]{"id,desc"};
        Response getBookFilterResponse = given()
                .baseUri(defaultBaseUrl)
                .accept("application/json")
                .contentType("application/json")
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sort", sort)
                .when().log().all().get("/api/book/filter");
        getBookFilterResponse.prettyPrint();

        assertEquals("status code is different with expected",
                200, getBookFilterResponse.getStatusCode());

        List<BookResponse> books = Arrays.asList(getBookFilterResponse.getBody().as(BookResponse[].class));

        if (size > books.size()) {
            assertEquals("size is different with expected", books.size(), books.size());
        } else {
            assertEquals("size is different with expected", size, books.size());
        }

        List<String> actualSort = books.stream().map(BookResponse::getId)
                .collect(Collectors.toList());
        List<String> expectedSort = books.stream()
                .map(BookResponse::getId).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        assertEquals("list is different with expected", expectedSort, actualSort);
    }
}
