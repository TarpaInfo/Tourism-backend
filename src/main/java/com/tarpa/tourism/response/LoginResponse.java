package com.tarpa.tourism.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String token;
    private String message;

}