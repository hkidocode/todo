package com.example.todolist.mysql.dto;

import com.example.todolist.mysql.model.Task;
import com.example.todolist.mysql.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private String title;
    private String description;
    private Date date;

    public TaskDTO(Task task) {
        this.setTitle(task.getTitle());
        this.setDescription(task.getDescription());
        this.setDate(task.getDate());
    }
}
