package com.example.todolist.mysql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false, updatable = false)
    private Long idUser;

    @NotNull @Size(max = 40)  @Column(name = "full_name", length = 40, nullable = false)
    private String fullName;

    @NotNull  @Column(name = "role")
    @ManyToMany @JoinTable(name = "roles_user",
            joinColumns = @JoinColumn( name = "id_user" ),
            inverseJoinColumns = @JoinColumn( name = "id_role" ))
    private Set<Role> roles = new HashSet<>();

    @NotNull @Size(max = 70)
    @Column(name = "email", length = 70)
    @Email
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(max = 40)
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToMany(targetEntity = Task.class, mappedBy = "user")
    private List<Task> tasks = new ArrayList<>();

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }
}
