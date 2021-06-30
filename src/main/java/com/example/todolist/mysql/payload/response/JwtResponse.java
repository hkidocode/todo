package com.example.todolist.mysql.payload.response;

import lombok.Data;

import java.util.Set;

@Data
public class JwtResponse {

    private String accessToken;
    private String type = "Bearer";
    private Long id;
    private String email;
    private Set<String> roles;

    public JwtResponse(String accessToken, Long id, String email, Set<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}
