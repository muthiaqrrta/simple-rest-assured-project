package org.example.request;

import lombok.Data;

@Data
public class AuthorRequest {
    private String name;
    private String address;
    private String phoneNumber;
}
