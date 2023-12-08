package org.example.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorResponse {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
}
