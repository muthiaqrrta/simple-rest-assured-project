package org.example.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponse {
    private String id;
    private String title;
    private String authorId;
    private AuthorResponse author;
}