package com.example.todolist.mysql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", nullable = false, updatable = false)
    private Integer idRole;
    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20)
    private ERole name;
}
