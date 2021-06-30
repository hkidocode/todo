package com.example.todolist.mysql.payload.request;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {

    @NotNull
    @Size(max = 40)
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @Size(max = 70)
    @Email
    private String email;

    private Set<String> role;

    @NotNull
    @Size(min = 6, max = 40)
    private String password;

}
