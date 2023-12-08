package org.example.request;

import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String authorId;
    private AuthorRequest author;
}
