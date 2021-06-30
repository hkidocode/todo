package com.example.todolist.mysql.dto;

import com.example.todolist.mysql.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String fullName;
    private String email;
    private String password;
    private List<TaskDTO> tasks = new ArrayList<>();

    public UserDTO(User user) {
        this.setFullName(user.getFullName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setTasks(user.getTasks().stream().map(TaskDTO::new).collect(Collectors.toList()));
    }
}
