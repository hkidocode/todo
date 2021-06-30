package com.example.todolist.mysql.repository;

import com.example.todolist.mysql.model.ERole;
import com.example.todolist.mysql.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(ERole name);
}
